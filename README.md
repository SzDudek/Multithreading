# Multithreading
The program was created as an assigned project for the university.  It is focused on the synchronization of multiple threads. It is designed to act as a simulator for a simple game that involves defending the end fields of the pitch from incoming balls by players of opposing teams on two sides.
Players move in vertical directions and do not overlap. Each of them is a separate thread. The balls are also threads, they spawn in the middle and move in the horizontal dimension. The initial direction of their movement is random. The balls arriving at the second or penultimate column may bounce off the players and change their direction of movement if only the players are there. If the field is empty in the penultimate or last column, the balls arrive at the first or last column and end their movement there ,,scoring'' a point. Next to every row of the pitch, there are indicators of the scored points on both sides.
<p align="center">
  <img src="https://github.com/SzDudek/Multithreading/blob/master/simulator.png" />
    <br/> X symbols - players, # symbols - balls
</p>
<br/>
The program is parametrized:

- the count of players in the team can be adjusted
- the boundary of the maximum ball number can be set
- velocities of players and balls are adjustable
- the size of the pitch can be modified

