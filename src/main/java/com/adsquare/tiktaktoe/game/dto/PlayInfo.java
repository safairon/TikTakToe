package com.adsquare.tiktaktoe.game.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayInfo {
    @NotNull
    Player player;

    @Min(value = 0, message = "Row must be at least 0")
    @Max(value = 2, message = "Row must be at most 2")
    int row;

    @Min(value = 0, message = "Column must be at least 0")
    @Max(value = 2, message = "Column must be at most 2")
    int column;

}
