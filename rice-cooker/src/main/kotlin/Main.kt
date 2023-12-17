package org.example
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

enum class RiceCookerState {
    IDLE,
    COOKING,
    WARM
}

enum class Commands {
    COOK,
    WARM,
    CANCEL,
    ADD_WATER,
    PLUG_IN,
    UNPLUG,
    EXIT
}

enum class ErrorCodes {
    NO_WATER,
    NOT_PLUGGED_IN,
    BUSY
}

class RiceCooker {
    private var state: RiceCookerState = RiceCookerState.IDLE
    private var hasWater: Boolean = true
    private var isPluggedIn: Boolean = true

    suspend fun cookRice() {
        if (!isPluggedIn) {
            println("Error: ${ErrorCodes.NOT_PLUGGED_IN} - Rice cooker is not plugged in.")
            return
        }

        if (!hasWater) {
            println("Error: ${ErrorCodes.NO_WATER} - No water detected. Please add water and try again.")
            return
        }

        if (state != RiceCookerState.IDLE) {
            println("Rice cooker is already busy.")
            return
        }

        state = RiceCookerState.COOKING
        println("Cooking rice...")

        // Simulate cooking time
        delay(3000)

        println("Rice is cooked!")
        state = RiceCookerState.IDLE
    }

    suspend fun warmRice() {
        if (!isPluggedIn) {
            println("Error: ${ErrorCodes.NOT_PLUGGED_IN} - Rice cooker is not plugged in.")
            return
        }

        if (!hasWater) {
            println("Error: ${ErrorCodes.NO_WATER} - No water detected. Please add water and try again.")
            return
        }

        if (state != RiceCookerState.IDLE && state != RiceCookerState.WARM) {
            println("Rice cooker is not ready for warming.")
            return
        }

        state = RiceCookerState.WARM
        println("Warming rice...")

        // Simulate warming time
        delay(1000)

        println("Rice is warm and ready to serve!")
        state = RiceCookerState.IDLE
    }

    fun cancel() {
        if (state == RiceCookerState.IDLE) {
            println("Nothing to cancel.")
            return
        }

        println("Canceling operation...")
        state = RiceCookerState.IDLE
        println("Operation canceled.")
    }

    fun addWater() {
        hasWater = true
        println("Water added successfully.")
    }

    fun plugIn() {
        isPluggedIn = true
        println("Rice cooker plugged in.")
    }

    fun unplug() {
        if (state != RiceCookerState.IDLE) {
            println("Error: ${ErrorCodes.BUSY} - Rice cooker is busy. Please wait until it finishes before unplugging.")
            return
        }

        isPluggedIn = false
        println("Rice cooker unplugged.")
    }

    fun printMenu() {
        println("\nMenu:")
        println("1. Cook Rice")
        println("2. Warm Rice")
        println("3. Cancel")
        println("4. Add Water")
        println("5. Plug In")
        println("6. Unplug")
        println("7. Exit")
    }

    suspend fun handleCommand(command: Int) {
        when (command) {
            1 -> cookRice()
            2 -> warmRice()
            3 -> cancel()
            4 -> addWater()
            5 -> plugIn()
            6 -> unplug()
            7 -> {
                println("Exiting Rice Cooker CLI. Goodbye!")
                System.exit(0)
            }
            else -> println("Invalid command. Please enter a valid option.")
        }
    }
}

fun main() = runBlocking {
    val cooker = RiceCooker()

    while (true) {
        cooker.printMenu()
        print("Enter your command: ")
        val userChoice = readLine()

        if (userChoice == null) {
            println("Invalid input. Please enter a command.")
            continue
        }

        val command = userChoice.trim().toIntOrNull()

        if (command == null) {
            println("Invalid input. Please enter a number.")
        } else {
            cooker.handleCommand(command)
        }
    }
}
