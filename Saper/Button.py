import pygame

class Button:
    def __init__(self, x, y, w, h, text, font):
        self.rect = pygame.Rect(x, y, w, h)
        self.text = text
        self.font = font
        self.inactive_color = pygame.color.Color("black")
        self.active_color = pygame.color.Color("red")
        self.current_color = self.inactive_color

    def draw(self, screen):
        mouse_pos = pygame.mouse.get_pos()
        color = self.active_color if self.rect.collidepoint(mouse_pos) else self.inactive_color
        pygame.draw.rect(screen, "white", self.rect)
        text_surface = self.font.render(self.text, True, color)
        screen.blit(text_surface, (
            self.rect.x + (self.rect.width - text_surface.get_width()) // 2,
            self.rect.y + (self.rect.height - text_surface.get_height()) // 2
        ))

    def is_clicked(self, event):
        if event.type == pygame.MOUSEBUTTONDOWN:
            return self.rect.collidepoint(event.pos)
        return False
