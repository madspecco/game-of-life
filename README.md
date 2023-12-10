# Game of Life
In this "Game of life" project we are going to simulate a population of living cells, each with the goal of feeding and reproducing.

## Team Members
##### Bădilă Timotei
##### Boloș Sergiu (TL)
##### Caia Raul
##### Chiș Horia

## Specifications
* ✅ there is a limited number of food units (resources) that cells must consume
* ✅ a food unit sustains a cell for a given time **T_full**, after which it gets hungry
* ✅ if it doesn't eat within another specified time **T_starve**, the cell dies, resulting it to drop a random number of food units between 1 and 5
* ✅ after eating at least 10 times, a cell will multiply before getting hungry again
* ✅ there are two types of cells: sexuate and asexuate:
    * ✅ **asexuate** cells multiply through division, resulting in two hungry cells
    * ✅ **sexuate** cells only multiply if they encounter another cell looking to reproduce, resulting in a third cell that was initially hungry
* ✅ in the simulation, cells will be represented by distinct threads of execution

## Potential Concurrency Problems
#### 1. Race Conditions
Since cells must be represented as distinct threads of execution, multiple cells might access shared resources simultaneously. This can lead to race conditions where the order of access determines the outcome, potentially resulting in incorrect behavior.

#### 2. Synchronization
Managing the behaviour of the cells might require complex synchronization mechanisms to ensure that the simulation proceeds correctly. Synchronization issues can lead to unpredictable behavior and bugs.

#### 3. Data Consistency
When multiple threads are modifying the same data, ensuring data consistency is crucial. Inconsistent data can lead to incorrect results and erratic behavior in your simulation.
 
#### 4. Resource Contention
Limited food units and the potential for multiple cells trying to access them concurrently could lead to resource contention. If not managed properly, this could cause inefficient resource utilization.

#### 5. Deadlocks
If cells need to acquire multiple resources in a specific order and get stuck waiting for each other to release resources, you could run into deadlocks. For example, if a cell needs to acquire food units and a reproduction resource, and another cell is waiting for the same resources in the reverse order, they might get stuck.

## Architecture
#### 1. Cell Representation
Define the characteristics and behavior of cells, including their type (asexuate or sexuate), hunger, reproduction cycle, and more.

#### 2. Food Management
Implement a system to manage food units, their availability, and distribution among cells.

#### 3. Simulation Control
We need to develop the main simulation loop or manager that controls the progression of time, cell actions, and interactions.

#### 4. Concurrency Control
We are going to use synchronization mechanisms, such as mutexes or semaphores, to manage access to shared resources, ensuring that multiple threads can interact without conflicts.

#### 5. Reproduction Logic
Implement the logic for cell reproduction, which depends on cell type and their interaction with other cells.

#### 6. Logging and Visualization
Consider how we'll log events and visualize the simulation to monitor and understand what's happening.
