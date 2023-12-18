import java.util.*

enum class RiceCookerState {
    IDLE, COOKING, WARM
}

enum class Commands {
    COOK, WARM, CANCEL, ADD_WATER, PLUG_IN, UNPLUG, EXIT
}

enum class ErrorCodes {
    NO_WATER, NOT_PLUGGED_IN, BUSY
}

class RiceCooker {
    private var _state: RiceCookerState = RiceCookerState.IDLE
    var state: RiceCookerState
        get() = _state
        set(value) {
            _state = value
        }
    private var _hasWater: Boolean = false
    var hasWater: Boolean
        get() = _hasWater
        set(value) {
            _hasWater = value
        }
    private var _isPluggedIn: Boolean = false
    var isPluggedIn: Boolean
        get() = _isPluggedIn
        set(value) {
            _isPluggedIn = value
        }

    fun cookRice() {
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

        Thread {
            Thread.sleep(3000) // Simulate cooking time (3 seconds)
            println("Rice is cooked!")
            state = RiceCookerState.IDLE
        }.start()
    }

    fun warmRice() {
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

        Thread {
            Thread.sleep(1000) // Simulate warming time (1 second)
            println("Rice is warm and ready to serve!")
            state = RiceCookerState.IDLE
        }.start()
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
}

fun main() {
    val scanner = Scanner(System.`in`)
    val cooker = RiceCooker()

    do {
        cooker.printMenu()
        print("Enter your command: ")
        val command = scanner.nextInt()

        when (command) {
            1 -> cooker.cookRice()
            2 -> cooker.warmRice()
            3 -> cooker.cancel()
            4 -> cooker.addWater()
            5 -> cooker.plugIn()
            6 -> cooker.unplug()
            7 -> println("Exiting Rice Cooker CLI. Goodbye!")
            else -> println("Invalid command. Please enter a valid option.")
        }

        Thread.sleep(100) // Allow threads to execute before prompting for the next input

    } while (command != Commands.EXIT.ordinal + 1)
}
