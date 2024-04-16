# EV3 LeJOS Software Development Project HAMK0324

![Robotics](https://img.shields.io/badge/Robotics-EV3-green?style=flat&logo=lego)
![Java version](https://img.shields.io/badge/Java%20version-1.7%2B-007396?style=flat&logo=java)
![GitHub repo size](https://img.shields.io/github/repo-size/SawsanAbdulbari/ev3_lejos_software_development_project_hamk0324?color=blue&logo=github)
![Type of Project](https://img.shields.io/badge/Type%20of%20Project-Software%20Development-orange?style=flat)
![License](https://img.shields.io/badge/License-MIT-green?style=flat)
[![Open Source Love](https://badges.frapsoft.com/os/v1/open-source.svg?v=103)](https://github.com/ellerbrock/open-source-badges/)




This repository hosts the software development project for controlling LEGO EV3 robots using LeJOS, Java for EV3. The project includes implementation of algorithms for navigating, following lines, and performing tasks in an environment. This project has been completed for educational purposes.

<br>
<p align="left">
  <img src="/ev3lego.webp" alt="banner" width="600"/>
</p>

[Img src](https://www.google.com/search?sca_esv=02e6ca09a22e3908&rlz=1C1GCEA_enFI1101FI1101&sxsrf=ACQVn08vEFxtfL2MS9NaJwA7ycbfN5kfgg:1711655266646&q=ev3+projects+with+instructions&tbm=isch&source=lnms&prmd=ivnbz&sa=X&ved=2ahUKEwjWy5Xh3JeFAxXXKBAIHUwbD4AQ0pQJegQIChAB&biw=1680&bih=915&dpr=1#imgrc=bGik7NIH6P3zfM&imgdii=thINmYZNJe0ZKM)


## Project Details
### **Professor:** Tommi Lahti
### **Professor:** Deepak KC
- **Courses:** Applied application project & Software Development Tools
- **Academic Year:** 2024

## Project team
- ### Sawsan Abdulbari
- ### Linda Marin
- ### Sonja Lahti
- ### Olga Hakasuo


## Description

The project aims to demonstrate basic capabilities of LEGO EV3 robots by using Java and the LeJOS EV3 library. It covers software development practices, algorithms for robot navigation, sensor integration, and motor control.

## Overview

This project involves the development of a LEGO EV3 robot capable of following a line on the ground while also detecting and navigating around an obstacle. The robot uses an EV3 color sensor for line tracking and an ultrasonic sensor for obstacle detection.

## Table of Contents

1. [Project Details](#project-details)
2. [Project Team](#project-team)
3. [Description](#description)
4. [Overview](#overview)
5. [Hardware Requirements](#hardware-requirements)
6. [Software Requirements](#software-requirements)
7. [Installation and Setup](#installation-and-setup)
8. [Project Structure](#project-structure)
9. [Installation for this repo](#installation-for-this-repo)
10. [Contribution Guidelines](#contribution-guidelines)
11. [MainClass.java](#mainclassjava)
12. [colorSensor.ColorSensor.java](#colorsensorcolorsensorjava)
13. [motors.MotorControl.java](#motorsmotorcontroljava)
14. [data.Config.java](#dataconfigjava)
15. [ultrasonicSensor.UltrasonicSensor.java](#ultrasonicsensorultrasonicsensorjava)
16. [LCD.LCDandSound.java](#lcdlcdandsoundjava)
17. [Usage](#usage)
18. [Calibration Process](#calibration-process)
19. [Troubleshooting](#troubleshooting)
20. [Challenges](#challenges)
21. [Future Improvements](#future-improvements)
22. [References](#references)

## Hardware Requirements

- LEGO EV3 Brick
- EV3 Color Sensor
- EV3 Ultrasonic Sensor
- Motors: 2x EV3 Large Motors

## Software Requirements

- LeJOS EV3 Firmware (version: leJOS_EV3_0.9.1-beta_win32)
- Java JDK (version: 1.7 (32 bit version))
- Eclipse IDE for enterprise with LeJOS plugin (eclipse-jee-2023-12-R-win32-x86_64)

## Installation and Setup

1. LeJOS EV3 Firmware: Install the LeJOS firmware on your EV3 Brick following the instructions from the [official LeJOS wiki](https://sourceforge.net/p/lejos/wiki/Home/).
2. Development Environment: Set up your Java development environment. If using Eclipse, install the LeJOS plugin following these instructions: [Eclipse with LeJOS setup](https://sourceforge.net/p/lejos/wiki/Installing%20the%20Eclipse%20plugin/).
3. Connect and Transfer: Connect the EV3 Brick to your computer via USB. Use the LeJOS tool to compile and transfer the .jar file to the brick.

## Project Structure

The project is organized into modules for easy navigation and development:

ev3_lejos_software_development_project_hamk0324/

```
EV3LeJOSProjectTeam17_0324
│ 
├───docs
└───src
│ ├───data
│ ├───main
│ │ ├───java
│ │ │ ├───colorSensor
│ │ │ ├───LCD
│ │ │ ├───motors
│ │ │ └───ultraSonicSensor
│ │ └───test
│ │ └───sensors
│ └───resources
│
│
├── Media
├── ContributionGuidelines.md
├── .gitignore
├── LICENSE
└── README.md
```

## Installation for this repo

Clone this repository and navigate into the project directory:

1. Clone the repository:

```bash
git clone https://github.com/SawsanAbdulbari/ev3_lejos_software_development_project_hamk0324.git
cd ev3_lejos_software_development_project_hamk0324
```

2. Navigate to the project directory:
   
```bash
cd your-repo
```

## Contribution Guidelines:

For contributing to this project, please see our [Contribution Guidelines](ContributionGuidelines.md).

### MainClass.java

This is the main entry point of the application. The MainClass initialises and starts separate threads for LCD and sound feedback, color sensing, motor control, and ultrasonic distance sensing based on a shared configuration. Monitors the application state and gracefully shuts down all components upon termination.

### colorSensor.ColorSensor.java

ColorSensor class handles the operations related to the EV3 color sensor including initialization, calibration, and continuous sensor value reading. Calibration involves setting up the sensor to detect the line and the floor by calculating thresholds based on sensor readings. This class is designed to be run on a separate thread to continuously update the sensor values in the application's configuration.

### motors.MotorControl.java

The MotorControl class is responsible for the direct control of the motors attached to an EV3 robot, facilitating both basic and complex movement patterns. It implements a PID controller to adjust the robot's speed and direction dynamically based on sensor feedback, aiming to achieve precise navigation. This class also includes methods for obstacle avoidance, making use of sensor data to alter the robot's path when necessary. It is designed to run on a separate thread to continuously monitor and adjust the robot's movement as needed based on the current environment and objectives.

### data.Config.java

Config class serves as the centralized storage for the application's configuration and state. It holds various parameters for line following and obstacle avoidance behaviors, including sensor thresholds, PID control parameters, and flags for application state. Methods are synchronized to ensure thread safety.

### ultrasonicSensor.UltrasonicSensor.java

Monitors for obstacles in front of the robot and signals the main control to execute avoidance strategies.

### LCD.LCDandSound.java

Uses stopwatch to calculate the timing for completing the course and plays a song during the driving process.

## Usage

1. Power on the EV3 Brick.
2. Navigate to the Programs folder and select MainClass.
3. Follow on-screen instructions for calibrating the color sensor.
4. Once calibration is complete, the robot will start following the line automatically.
5. To stop the robot, press the escape button on the EV3 Brick.

## Calibration Process

Calibration is necessary for the color sensor to accurately detect the line on different surfaces. The process involves placing the robot on the line and then on the floor off the line, pressing a button each time to record the color values.

## Troubleshooting

- Robot veers off line: Check the calibration values and ensure the lighting conditions are consistent.
- Obstacle detection not working: Verify the ultrasonic sensor is correctly connected and not obstructed.

### Challenges

- Sensor noise impacted the line-following precision. This was optimized by implementing a PID control algorithm.

### Future Improvements

- Implement dynamic obstacle navigation strategies to handle more complex environments.

- Break class files into smaller classes for a more comprehesive and clean structure.

  - Assign a new class for PID controls

- Divide existing methods in each class into more organized, smaller methods for code cleanliness and development optimization.

### References

- LeJOS EV3 API Documentation: [LeJOS API](http://www.lejos.org/ev3/docs/)
- Effective Java by Joshua Bloch was instrumental in understanding Java best practices for this project.
- HAMK course materials
- Applied application project
- Software Development Tools
 
