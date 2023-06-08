package com.assignment.nl22w.game.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cell {

    private int row;
    private int column;
    private int distance;
    private Cell previous;
}
