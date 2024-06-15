import pygame
import random

from Button import Button
from FieldButton import FieldButton
from InputBox import InputBox

pygame.init()

DEFAULT_WIDTH, DEFAULT_HEIGHT = 800, 600
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
GRAY = (200, 200, 200)
RED = (255, 0, 0)
LIGHT_GRAY = (220, 220, 220)

font = pygame.font.SysFont(None, 24)


class Minesweeper:
    def __init__(self, width, height, grid_size, num_mines):
        self.width = width
        self.height = height
        self.grid_size = grid_size
        self.num_mines = num_mines
        self.cell_size = min(width // grid_size, height // grid_size)
        self.screen = pygame.display.set_mode((width, height), pygame.RESIZABLE)
        pygame.display.set_caption("Saper")
        self.grid = self.create_grid()
        self.hovered_cell = None
        self.game_over = False
        self.mines_left = num_mines

    def create_grid(self):
        grid = [[FieldButton() for _ in range(self.grid_size)] for _ in range(self.grid_size)]
        mines = 0
        while mines < self.num_mines:
            x = random.randint(0, self.grid_size - 1)
            y = random.randint(0, self.grid_size - 1)
            if not grid[y][x].is_mine:
                grid[y][x].is_mine = True
                mines += 1
                for i in range(max(0, y - 1), min(self.grid_size, y + 2)):
                    for j in range(max(0, x - 1), min(self.grid_size, x + 2)):
                        if not grid[i][j].is_mine:
                            grid[i][j].adjacent_mines += 1
        return grid

    def draw_grid(self):
        for y in range(self.grid_size):
            for x in range(self.grid_size):
                rect = pygame.Rect(x * self.cell_size, y * self.cell_size, self.cell_size, self.cell_size)
                cell = self.grid[y][x]
                if cell.is_revealed:
                    pygame.draw.rect(self.screen, GRAY, rect)
                    if cell.is_mine:
                        pygame.draw.circle(self.screen, RED, rect.center, self.cell_size // 4)
                    elif cell.adjacent_mines > 0:
                        text = font.render(str(cell.adjacent_mines), True, BLACK)
                        self.screen.blit(text, rect.topleft)
                else:
                    color = LIGHT_GRAY if self.hovered_cell == (x, y) else WHITE
                    pygame.draw.rect(self.screen, color, rect)
                    if cell.is_flagged:
                        pygame.draw.circle(self.screen, BLACK, rect.center, self.cell_size // 4)
                pygame.draw.rect(self.screen, BLACK, rect, 1)

    def reveal_cell(self, x, y):
        cell = self.grid[y][x]
        if cell.is_mine:
            self.game_over = True
            for row in self.grid:
                for cell in row:
                    cell.is_revealed = True
        else:
            cell.is_revealed = True
            if cell.adjacent_mines == 0:
                stack = [(y, x)]
                while stack:
                    cy, cx = stack.pop()
                    for i in range(max(0, cy - 1), min(self.grid_size, cy + 2)):
                        for j in range(max(0, cx - 1), min(self.grid_size, cx + 2)):
                            neighbor = self.grid[i][j]
                            if not neighbor.is_revealed and not neighbor.is_mine:
                                neighbor.is_revealed = True
                                if neighbor.adjacent_mines == 0:
                                    stack.append((i, j))

    def toggle_flag(self, x, y):
        cell = self.grid[y][x]
        cell.is_flagged = not cell.is_flagged

    def save_game(self, filename):
        try:
            with open(filename, 'w') as f:
                for row in self.grid:
                    for cell in row:
                        f.write(
                            f"{int(cell.is_mine)} {cell.adjacent_mines} {int(cell.is_revealed)} {int(cell.is_flagged)} ")
                    f.write('\n')
        except IOError as e:
            print(f"Error saving game: {e}")

    def load_game(self, filename):
        try:
            with open(filename, 'r') as f:
                for y, line in enumerate(f):
                    data = list(map(int, line.split()))
                    for x in range(self.grid_size):
                        self.grid[y][x].is_mine = bool(data[4 * x])
                        self.grid[y][x].adjacent_mines = data[4 * x + 1]
                        self.grid[y][x].is_revealed = bool(data[4 * x + 2])
                        self.grid[y][x].is_flagged = bool(data[4 * x + 3])
        except IOError as e:
            print(f"Error loading game: {e}")

    def run(self):
        running = True
        while running:
            self.screen.fill(WHITE)
            self.draw_grid()

            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    running = False
                elif event.type == pygame.VIDEORESIZE:
                    self.screen = pygame.display.set_mode((event.w, event.h), pygame.RESIZABLE)
                elif event.type == pygame.MOUSEMOTION:
                    x, y = event.pos
                    grid_x = x // self.cell_size
                    grid_y = y // self.cell_size
                    if grid_x < self.grid_size and grid_y < self.grid_size:
                        self.hovered_cell = (grid_x, grid_y)
                    else:
                        self.hovered_cell = None
                elif event.type == pygame.MOUSEBUTTONDOWN and not self.game_over:
                    x, y = event.pos
                    grid_x = x // self.cell_size
                    grid_y = y // self.cell_size

                    if grid_x < self.grid_size and grid_y < self.grid_size:
                        if event.button == 1:
                            self.reveal_cell(grid_x, grid_y)
                        elif event.button == 3:
                            self.toggle_flag(grid_x, grid_y)

            pygame.display.flip()

        pygame.quit()


def show_menu():
    screen = pygame.display.set_mode((DEFAULT_WIDTH, DEFAULT_HEIGHT))
    pygame.display.set_caption("Saper - Menu")

    font = pygame.font.SysFont(None, 48)
    options = ["Latwy", "Sredni", "Trudny", "Wlasne"]
    buttons = [
        Button(DEFAULT_WIDTH // 2 - 100, DEFAULT_HEIGHT // 2 - 100 + idx * 60, 200, 50, option, font)
        for idx, option in enumerate(options)
    ]
    row_length_input = InputBox(DEFAULT_WIDTH // 2 - 150, DEFAULT_HEIGHT // 2 - 100 + 240, 140, 30, "dlugosc pola gry")
    bombs_amount_input = InputBox(DEFAULT_WIDTH // 2 + 10, DEFAULT_HEIGHT // 2 - 100 + 240, 140, 30, "ilosc bomb")

    while True:
        screen.fill(WHITE)

        for button in buttons:
            button.draw(screen)

        row_length_input.draw(screen)
        bombs_amount_input.draw(screen)
        pygame.display.flip()

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                return None
            elif event.type == pygame.MOUSEBUTTONDOWN or event.type == pygame.KEYDOWN:
                row_length_input.handle_event(event)
                bombs_amount_input.handle_event(event)
                for button in buttons:
                    if button.is_clicked(event):
                        if button.text == "Latwy":
                            return 400, 400, 10, 10
                        elif button.text == "Sredni":
                            return 640, 640, 16, 40
                        elif button.text == "Trudny":
                            return 800, 800, 20, 80
                        elif button.text == "Wlasne":
                            if int(row_length_input.text) ** 2 > int(bombs_amount_input.text):
                                return (int(row_length_input.text) * 60, int(row_length_input.text) * 60,
                                        int(row_length_input.text), int(bombs_amount_input.text))


def main():
    game_settings = show_menu()
    if game_settings:
        width, height, grid_size, num_mines = game_settings
        game = Minesweeper(width + 100, height, grid_size, num_mines)
        game.run()


if __name__ == "__main__":
    main()
