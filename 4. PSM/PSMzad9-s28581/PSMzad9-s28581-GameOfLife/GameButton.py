import pygame


class GameButton:
    def __init__(self, x, y, length, space):
        self.x = x
        self.y = y
        self.length = length
        self.space = space
        self.rect = pygame.Rect(self.x, self.y, length, length)
        self.alive = False
        # setting color to gray
        self.color = pygame.Color(128, 128, 128, 255)

    def set_color(self, color):
        self.color = color

    def change_state(self):
        if self.alive:
            self.alive = False
            self.color = pygame.Color(128, 128, 128, 255)
        else:
            self.alive = True
            self.color = pygame.Color(144, 238, 144, 255)

    def draw(self, screen):
        pygame.draw.rect(screen, self.color, self.rect)

    def __str__(self):
        return str('x: ' + str(self.x) + ' y: ' + str(self.y))

    def check_neighbors(self, table: [[]]) -> int:
        x = self.x / (self.length + self.space)
        y = self.y / (self.length + self.space)
        alive_counter = 0
        for i in range(-1, 1):
            for j in range(-1, 1):
                if not (i == 0 and j == 0):
                    if x + i < len(table[0]) - 1 and y + j < len(table) - 1:
                        if table[int(y + j)][int(x + i)].alive:
                            alive_counter += 1
        return alive_counter

    def check_rules(self, rule, table):
        str_becoming_alive, str_still_alive = rule.split('/')
        becoming_alive = [int]
        still_alive = [int]
        for i in str_becoming_alive:
            becoming_alive.append(int(i))
        for i in str_still_alive:
            still_alive.append(int(i))
        neighbors = self.check_neighbors(table)
        if self.alive and not still_alive.__contains__(neighbors):
            self.change_state()
        elif not self.alive and becoming_alive.__contains__(neighbors):
            self.change_state()
