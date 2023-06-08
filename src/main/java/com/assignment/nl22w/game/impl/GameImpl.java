package com.assignment.nl22w.game.impl;

import com.assignment.nl22w.game.Game;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
@Slf4j
public class GameImpl implements Game {

	@Override
	public int escapeFromTheWoods(Resource resource) throws IOException {
		String[][] matrix = readGameFileTo2DArray(resource.getFile());
		return findShortestDistance(matrix);
	}

	private static String[][] readGameFileTo2DArray(File file) throws IOException {
		int rowLength = 0;
		String line = "";
		Scanner scanner = new Scanner(file);
		List<String> readFileList = new ArrayList<>();
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			readFileList.add(line);
			++rowLength;
		}
		int columnLength = readFileList.get(0).trim().split("").length;
		String[] array;
		String[][] matrix = new String[rowLength][columnLength];
		for (int i = 0; i < matrix.length; i++) {
			array = readFileList.get(i).trim().split("");
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = array[j];
			}
		}
		return matrix;
	}


	private static int findShortestDistance(String[][] matrix) {
		int shortestDistance = Integer.MAX_VALUE;
		boolean hasExit = false;
		int[] start = findStartPosition(matrix);
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if ((i == 0 || j == 0 || i == matrix.length - 1 || j == matrix[0].length - 1) && (" ".equals(matrix[i][j]))) {
					hasExit = true;
					int[] end = new int[]{i, j};
					int distance = searchShortestPath(matrix, start, end);
					if (shortestDistance > distance) {
						shortestDistance = distance;
					}
				}
			}
		}
		if (hasExit) {
			return shortestDistance;
		}
		return -1;
	}

	private static int searchShortestPath(String[][] matrix, int[] start, int[] end) {
		LinkedList<Cell> path = new LinkedList<>();
		int startRow = start[0];
		int startColumn = start[1];
		int endRow = end[0];
		int endColumn = end[1];
		if ("1".equals(matrix[startRow][startColumn]) || "1".equals(matrix[endRow][endColumn])) {
			return -1;
		}
		int m = matrix.length;
		int n = matrix[0].length;
		Cell[][] cells = new Cell[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (" ".equals(matrix[i][j]) || "X".equals(matrix[i][j])) {
					cells[i][j] = new Cell(i, j, Integer.MAX_VALUE, null);
				}
			}
		}
		LinkedList<Cell> queue = new LinkedList<>();
		Cell startCell = cells[startRow][startColumn];
		startCell.setDistance(0);
		queue.add(startCell);
		Cell nextCell = null;
		Cell currentCell;
		while ((currentCell = queue.poll()) != null) {
			if (currentCell.getRow() == endRow && currentCell.getColumn() == endColumn) {
				nextCell = currentCell;
				break;
			}
			visitNextCell(cells, queue, currentCell.getRow() - 1, currentCell.getColumn(), currentCell);
			visitNextCell(cells, queue, currentCell.getRow() + 1, currentCell.getColumn(), currentCell);
			visitNextCell(cells, queue, currentCell.getRow(), currentCell.getColumn() - 1, currentCell);
			visitNextCell(cells, queue, currentCell.getRow(), currentCell.getColumn() + 1, currentCell);
		}
		if (nextCell == null) {
			return -1;
		} else {
			currentCell = nextCell;
			do {
				path.addFirst(currentCell);
			} while ((currentCell = currentCell.getPrevious()) != null);
		}
		return path.size() - 1;
	}

	private static void visitNextCell(Cell[][] cells, LinkedList<Cell> queue, int row, int column, Cell parent) {
		if (row < 0 || row >= cells.length || column < 0 || column >= cells[0].length || cells[row][column] == null) {
			return;
		}
		int distance = parent.getDistance() + 1;
		Cell currentCell = cells[row][column];
		if (distance < currentCell.getDistance()) {
			currentCell.setDistance(distance);
			currentCell.setPrevious(parent);
			queue.add(currentCell);
		}
	}


	private static int[] findStartPosition(String[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if ("X".equals(matrix[i][j])) {
					return new int[]{i, j};
				}
			}
		}
		return new int[]{-1, -1};
	}
}
