
// Data class for the Task
// This uses variables, arguments and classes
data class Task(
    val id: Int,
    var title: String,
    var done: Boolean = false
) {
    fun display(): String {
        val status = if (done) "✓" else "✗"
        return "#${id} [$status] ${title}"
    }
}

// This class with manage a collection of Tasks
// We will use expressions, conditionals, loops, functions, and a class with state
//These are my data handling functions, they can't interact with the user
class TaskManager {
    private val tasks = mutableListOf<Task>()
    private var nextId = 1

    fun add(title: String): Task {
        //This function generates a unique id
        val task = Task(id = nextId++, title = title.trim())
        tasks += task
        return task
    }
    //This function removes a task
    fun remove(id: Int): Boolean {
        val removed = tasks.removeIf { it.id == id }
        return removed
    }
    //This function makes a list of the tasks
    fun list(): List<Task> = tasks.toList()

    //This function will mark the task as done
    fun markDone(id: Int, value: Boolean = true): Boolean {
        val t = tasks.firstOrNull { it.id == id }
        return if (t != null) { t.done = value; true } else { false }
    }

    fun isEmpty(): Boolean = tasks.isEmpty()
}

//This is for the console to make sure the value inputted was not blank
fun readNonBlankLine(prompt: String): String {
    while (true) {
        print(prompt)
        val line = readLine()?.trim() ?: ""
        if (line.isNotEmpty()) return line
        println("Input cannot be blank. Try again.")
    }
}
//This function helps with the Remove handler, it makes sure a valid number is given.
//It also reads the right integer for the id to toggle the task from undone to done
fun readInt(prompt: String): Int {
    while (true) {
        print(prompt)
        val input = readLine()?.trim()
        val num = input?.toIntOrNull()
        if (num != null) return num
        println("Please enter a valid number.")
    }
}
//This is the menu for the Task Manger
fun showMenu() {
    println()
    println("==== Task Manager ====")
    println("1) Add task")
    println("2) Remove task")
    println("3) List tasks")
    println("4) Mark task done/undone")
    println("5) Exit")
}
//These add, remove, list and toggle functions are Handler functions and will be what interacts with the user
//This helps the task manager to be cleaner, but we could write the two functions into one
fun handleAdd(tm: TaskManager) {
    val title = readNonBlankLine("Task title: ")
    val task = tm.add(title)
    println("Added: ${task.display()}")
}

fun handleRemove(tm: TaskManager) {
    if (tm.isEmpty()) {
        println("No tasks to remove.")
        return
    }
    val id = readInt("Enter task id to remove: ")
    if (tm.remove(id)) println("Removed task #$id") else println("Task #$id not found.")
}

fun handleList(tm: TaskManager) {
    val all = tm.list()
    if (all.isEmpty()) {
        println("No tasks yet. Add one!")
        return
    }
    println("\n-- Your Tasks --")
    for (t in all) {
        println(t.display())
    }
}

fun handleToggleDone(tm: TaskManager) {
    if (tm.isEmpty()) {
        println("No tasks to update.")
        return
    }
    val id = readInt("Enter task id to toggle: ")
    // Flip the done state using an expression
    val target = tm.list().firstOrNull { it.id == id }
    if (target == null) {
        println("Task #$id not found.")
    } else {
        val newValue = !target.done
        tm.markDone(id, newValue)
        println("Task #$id is now ${if (newValue) "DONE" else "NOT DONE"}.")
    }
}
//Loop function to interact with users, calls the showMenu function, and allows user to make selections
fun main() {
    val tm = TaskManager()

    loop@ while (true) { //Loop for the loop function
        showMenu()
        when (readLine()?.trim()) {
            "1" -> handleAdd(tm)
            "2" -> handleRemove(tm)
            "3" -> handleList(tm)
            "4" -> handleToggleDone(tm)
            "5", "q", "Q", "exit" -> {
                println("Goodbye!")
                break@loop
            }
            else -> println("Unknown option. Please choose 1-5.")
        }
    }
}
