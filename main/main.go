package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"time"
)

type RiceCookerState string

const (
	IDLE    RiceCookerState = "idle"
	COOKING RiceCookerState = "cooking"
	WARM    RiceCookerState = "warm"
)

type Commands int

const (
	COOK     Commands = 1
	CWARM    Commands = 2
	CANCEL   Commands = 3
	AddWater Commands = 4
	PlugIn   Commands = 5
	UNPLUG   Commands = 6
	EXIT     Commands = 7
)

type ErrorCodes int

const (
	NO_WATER       ErrorCodes = 101
	NOT_PLUGGED_IN ErrorCodes = 102
	BUSY           ErrorCodes = 103
)

type RiceCooker struct {
	state       RiceCookerState
	hasWater    bool
	isPluggedIn bool
}

func NewRiceCooker() *RiceCooker {
	return &RiceCooker{
		state:       IDLE,
		hasWater:    true,
		isPluggedIn: true,
	}
}

func (rc *RiceCooker) cookRice() {
	if !rc.isPluggedIn {
		fmt.Printf("Error: %d - Rice cooker is not plugged in.\n", NOT_PLUGGED_IN)
		return
	}

	if !rc.hasWater {
		fmt.Printf("Error: %d - No water detected. Please add water and try again.\n", NO_WATER)
		return
	}

	if rc.state != IDLE {
		fmt.Println("Rice cooker is already busy.")
		return
	}

	rc.state = COOKING
	fmt.Println("Cooking rice...")

	// Simulate cooking time
	go func() {
		<-time.After(3 * time.Second)
		fmt.Println("Rice is cooked!")
		rc.state = IDLE
	}()
}

func (rc *RiceCooker) warmRice() {
	if !rc.isPluggedIn {
		fmt.Printf("Error: %d - Rice cooker is not plugged in.\n", NOT_PLUGGED_IN)
		return
	}

	if !rc.hasWater {
		fmt.Printf("Error: %d - No water detected. Please add water and try again.\n", NO_WATER)
		return
	}

	if rc.state != IDLE && rc.state != WARM {
		fmt.Println("Rice cooker is not ready for warming.")
		return
	}

	rc.state = WARM
	fmt.Println("Warming rice...")

	// Simulate warming time
	go func() {
		<-time.After(1 * time.Second)
		fmt.Println("Rice is warm and ready to serve!")
		rc.state = IDLE
	}()
}

func (rc *RiceCooker) cancel() {
	if rc.state == IDLE {
		fmt.Println("Nothing to cancel.")
		return
	}

	fmt.Println("Canceling operation...")
	rc.state = IDLE
	fmt.Println("Operation canceled.")
}

func (rc *RiceCooker) addWater() {
	rc.hasWater = true
	fmt.Println("Water added successfully.")
}

func (rc *RiceCooker) plugIn() {
	rc.isPluggedIn = true
	fmt.Println("Rice cooker plugged in.")
}

func (rc *RiceCooker) unplug() {
	if rc.state != IDLE {
		fmt.Printf("Error: %d - Rice cooker is busy. Please wait until it finishes before unplugging.\n", BUSY)
		return
	}

	rc.isPluggedIn = false
	fmt.Println("Rice cooker unplugged.")
}

func (rc *RiceCooker) printMenu() {
	fmt.Println("\nMenu:")
	fmt.Println("1. Cook Rice")
	fmt.Println("2. Warm Rice")
	fmt.Println("3. Cancel")
	fmt.Println("4. Add Water")
	fmt.Println("5. Plug In")
	fmt.Println("6. Unplug")
	fmt.Println("7. Exit")
}

func main() {
	cooker := NewRiceCooker()

	scanner := bufio.NewScanner(os.Stdin)

	for {
		cooker.printMenu()

		fmt.Print("Enter your choice: ")
		scanner.Scan()
		input := scanner.Text()

		choice, err := strconv.Atoi(input)
		if err != nil {
			fmt.Println("Invalid input. Please enter a number.")
			continue
		}

		command := Commands(choice)

		switch command {
		case COOK:
			cooker.cookRice()
		case CWARM:
			cooker.warmRice()
		case CANCEL:
			cooker.cancel()
		case AddWater:
			cooker.addWater()
		case PlugIn:
			cooker.plugIn()
		case UNPLUG:
			cooker.unplug()
		case EXIT:
			fmt.Println("Exiting Rice Cooker CLI. Goodbye!")
			os.Exit(0)
		default:
			fmt.Println("Invalid command. Please enter a valid option.")
		}
	}
}
