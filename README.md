# Game of Life
In this "Game of life" project we are going to simulate a population of living cells, each with the goal of feeding and reproducing.

## Specifications
* there is a limited number of food units (resources) that cells must consume
* a food unit sustains a cell for a given time **T_full**, after which it gets hungry
* if it doesn't eat within another specified time **T_starve**, the cell dies, resulting it to drop a random number of food units between 1 and 5
* after eating at least 10 times, a cell will multiply before getting hungry again
* there are two types of cells: sexuate and asexuate:
    * **asexuate** cells multiply through division, resulting in two hungry cells
    * **sexuate** cells only multiply if they encounter another cell looking to reproduce, resulting in a third cell that was initially hungry
* in the simulation, cells will be represented by distinct threads of execution

## Potential Concurrency Problems
**TBA**

## Architecture
**TBA**
