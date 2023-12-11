import 'dart:io';

enum RiceCookerState { idle, cooking, warm }

enum Commands { cook, warm, cancel, addWatter, plugIn, unPlug, exit }

enum ErrorCodes { noWatter, notPluggedIn, busy }

class RiceCooker {
  RiceCookerState state = RiceCookerState.idle;
  bool hasWater = true;
  bool isPluggedIn = true;

  Future<void> cookRice() async {
    if (!isPluggedIn) {
      print('Error: ${ErrorCodes.notPluggedIn} - Rice cooker is not plugged in.');
      return;
    }

    if (!hasWater) {
      print('Error: ${ErrorCodes.noWatter} - No water detected. Please add water and try again.');
      return;
    }

    if (state != RiceCookerState.idle) {
      print('Rice cooker is already busy.');
      return;
    }

    state = RiceCookerState.cooking;
    print('Cooking rice...');

    // Simulate cooking time
    await Future.delayed(Duration(seconds: 3));

    print('Rice is cooked!');
    state = RiceCookerState.idle;
  }

  Future<void> warmRice() async {
    if (!isPluggedIn) {
      print('Error: ${ErrorCodes.notPluggedIn} - Rice cooker is not plugged in.');
      return;
    }

    if (!hasWater) {
      print('Error: ${ErrorCodes.noWatter} - No water detected. Please add water and try again.');
      return;
    }

    if (state != RiceCookerState.idle && state != RiceCookerState.warm) {
      print('Rice cooker is not ready for warming.');
      return;
    }

    state = RiceCookerState.warm;
    print('Warming rice...');

    // Simulate warming time
    await Future.delayed(Duration(seconds: 1));

    print('Rice is warm and ready to serve!');
    state = RiceCookerState.idle;
  }

  void cancel() {
    if (state == RiceCookerState.idle) {
      print('Nothing to cancel.');
      return;
    }

    print('Canceling operation...');
    state = RiceCookerState.idle;
    print('Operation canceled.');
  }

  void addWater() {
    hasWater = true;
    print('Water added successfully.');
  }

  void plugIn() {
    isPluggedIn = true;
    print('Rice cooker plugged in.');
  }

  void unplug() {
    if (state != RiceCookerState.idle) {
      print('Error: ${ErrorCodes.busy} - Rice cooker is busy. Please wait until it finishes before unplugging.');
      return;
    }

    isPluggedIn = false;
    print('Rice cooker unplugged.');
  }

  void printMenu() {
    print('\nMenu:');
    print('1. Cook Rice');
    print('2. Warm Rice');
    print('3. Cancel');
    print('4. Add Water');
    print('5. Plug In');
    print('6. Unplug');
    print('7. Exit');
  }

  Future<void> handleCommand(int command) async {
    switch (command) {
      case 1:
        await cookRice();
        break;
      case 2:
        await warmRice();
        break;
      case 3:
        cancel();
        break;
      case 4:
        addWater();
        break;
      case 5:
        plugIn();
        break;
      case 6:
        unplug();
        break;
      case 7:
        print('Exiting Rice Cooker CLI. Goodbye!');
        exit(0);
      default:
        print('Invalid command. Please enter a valid option.');
    }
  }
}

void main() async {
  final cooker = RiceCooker();

  while (true) {
    cooker.printMenu();
    stdout.write('Enter your command: ');
    final userChoice = stdin.readLineSync();

    if (userChoice == null) {
      print('Invalid input. Please enter a command.');
      continue;
    }

    final command = int.tryParse(userChoice.trim());

    if (command == null) {
      print('Invalid input. Please enter a number.');
    } else {
      await cooker.handleCommand(command);
    }
  }
}
