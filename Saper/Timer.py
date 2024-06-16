import time
import pygame

class Timer:
    def __init__(self):
        self.start_time = None
        self.elapsed_time = 0
        self.best_time = float('inf')
        self.font = pygame.font.Font(None, 36)

    def start(self):
        self.start_time = time.time()

    def stop(self):
        if self.start_time:
            self.elapsed_time = int(time.time() - self.start_time)
            if self.elapsed_time < self.best_time:
                self.best_time = self.elapsed_time
                self.save_best_time()

    def update(self):
        if self.start_time:
            self.elapsed_time = int(time.time() - self.start_time)

    def get_time(self):
        return self.elapsed_time

    def save_best_time(self):
        with open("best_time.txt", "w") as file:
            file.write(str(self.best_time))

    def load_best_time(self):
        try:
            with open("best_time.txt", "r") as file:
                self.best_time = int(file.read())
        except FileNotFoundError:
            self.best_time = float('inf')

    def draw(self, screen, board_width):
        time_text = self.font.render(f"Czas: {self.elapsed_time}s", True, (0, 0, 0))
        best_time_text = self.font.render(f"Najlepszy Czas: {self.best_time}s", True, (0, 0, 0))

        screen.blit(time_text, (board_width - 130, 10))
        screen.blit(best_time_text, (board_width - 130, 50))
