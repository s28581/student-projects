import pygame

pygame.init()

DEFAULT_WIDTH, DEFAULT_HEIGHT = 800, 600
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
GRAY = (200, 200, 200)
RED = (255, 0, 0)
LIGHT_GRAY = (220, 220, 220)

font = pygame.font.SysFont(None, 24)


class InputBox:
    def __init__(self, x, y, w, h, text):
        self.rect = pygame.Rect(x, y, w, h)
        self.color = pygame.Color('black')
        self.default_text = text
        self.text = text
        self.font = pygame.font.SysFont(None, 24)
        self.txt_surface = pygame.font.Font.render(self.font, self.default_text, True, self.color)
        self.active = False

    def handle_event(self, event):
        if event.type == pygame.MOUSEBUTTONDOWN and self.rect.collidepoint(event.pos):
            self.active = not self.active
            self.color = pygame.Color('red') if self.active else pygame.Color('black')
            self.txt_surface = font.render(self.text, True, self.color)

        if event.type == pygame.KEYDOWN:
            if self.active:
                if event.key == pygame.K_RETURN:
                    self.active = not self.active
                    self.color = pygame.Color('red') if self.active else pygame.Color('black')
                elif event.key == pygame.K_BACKSPACE:
                    if self.text == self.default_text:
                        self.text = ""
                    else:
                        self.text = self.text[:-1]
                elif event.unicode.isdigit():
                    if self.text == self.default_text:
                        self.text = ""
                    self.text += event.unicode
                self.txt_surface = font.render(self.text, True, self.color)
        if self.text == "":
            self.text = self.default_text

    def draw(self, screen):
        pygame.draw.rect(screen, self.color, self.rect, 2)
        font_image = pygame.font.Font.render(self.font, self.text, True, self.color)
        screen.blit(self.txt_surface, (self.rect.x + 5, self.rect.y + 5))
