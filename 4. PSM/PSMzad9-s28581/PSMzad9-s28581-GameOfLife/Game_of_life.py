import pygame

from FunctionButton import FunctionButton
import GameButton


def next_iteration(table: [[GameButton]], rule: str):
    for row in table:
        for cell in row:
            cell.check_rules(rule, table)


pygame.init()
width = 900
height = 650
size = 50
margin = 20
length = 10
space = length + 2
rule = '23/3'
screen = pygame.display.set_mode((width, height))
pygame.display.set_caption('Game of Life')
button = GameButton.GameButton(1, 2, length, space - length)
table = [[GameButton.GameButton(margin + i, margin + j, length, space - length) for i in range(0, size * space, space)] for j in
         range(0, size * space, space)]
next_iteration_button = FunctionButton(width - 175, 50, 150, 25, 'Next iteration')
run = True

screen.fill((200, 228, 241))
while run:
    # drawing buttons
    for y in table:
        for x in y:
            x.draw(screen)

    next_iteration_button.draw(screen)
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            run = False
        elif event.type == pygame.MOUSEBUTTONDOWN and margin < pygame.mouse.get_pos()[
            0] < margin + size * 11 and margin < pygame.mouse.get_pos()[1] < margin + size * 11:
            table[int((pygame.mouse.get_pos()[1] - margin) / space)][
                int((pygame.mouse.get_pos()[0] - margin) / space)].change_state()
        elif next_iteration_button.rect.collidepoint(pygame.mouse.get_pos()) and pygame.mouse.get_pressed()[0] == 1:
            next_iteration(table, rule)

    pygame.display.update()

pygame.quit()


