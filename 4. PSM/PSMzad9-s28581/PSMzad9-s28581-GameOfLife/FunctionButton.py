import pygame


class FunctionButton:
    def __init__(self, x, y, width, height, text):
        self.x = x
        self.y = y
        self.rect = pygame.Rect(self.x, self.y, width, height)
        self.text = pygame.font.SysFont('Georgia', 15).render(text, True, 'black')

    def draw(self, screen):
        pygame.draw.rect(screen, 'white', self.rect)
        screen.blit(self.text, self.rect)