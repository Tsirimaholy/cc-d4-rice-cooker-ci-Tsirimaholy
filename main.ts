import { Commands, RiceCooker } from './RiceCooker'
import readline from 'readline'

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
})

const cooker = new RiceCooker()

function processUserInput (): void {
  cooker.printMenu()

  rl.question('Enter your command: ', (answer) => {
    const userChoice = parseInt(answer.trim(), 10)

    if (isNaN(userChoice)) {
      console.log('Invalid input. Please enter a number.')
    } else {
      const command = userChoice as Commands
      cooker.handleCommand(command)
    }

    processUserInput()
  })
}

processUserInput()
