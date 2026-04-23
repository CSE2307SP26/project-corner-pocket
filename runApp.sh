#!/bin/bash

cd src || exit

javac main/*.java

java main.MainMenu
