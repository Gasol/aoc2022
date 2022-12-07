package tw.gasol.aoc.aoc2022

import java.util.Scanner

interface Filesystem {
    val name: String
    val size: Int
}

data class File(
    override val name: String,
    override val size: Int,
    val parent: Directory? = null
) : Filesystem

data class Directory(
    override val name: String,
    val children: MutableList<Filesystem> = mutableListOf(),
    val parent: Directory? = null
) : Filesystem {
    override val size = 0

    private fun findChildren(name: String): Filesystem? {
        return children.firstOrNull { it.name == name }
    }

    fun findChildrenRecursively(predicate: (Filesystem) -> Boolean): List<Filesystem> {
        return children.filter(predicate) + children.filterIsInstance<Directory>()
            .flatMap { it.findChildrenRecursively(predicate) }
    }

    fun getDirectory(name: String): Directory? {
        return findChildren(name) as Directory?
    }

    fun getTotalSize(): Int {
        return size + children.sumOf { if (it is Directory) it.getTotalSize() else it.size }
    }
}

class Day7 {
    private fun buildFilesystem(input: String): Filesystem {
        var currentDir: Directory? = null
        var rootDir: Directory? = null
        input.lineSequence()
            .filterNot { it.isBlank() }
            .forEach { line ->
                if (line.startsWith("$")) {
                    Scanner(line).use { scanner ->
                        scanner.next()
                        when (val command = scanner.next()) {
                            "cd" -> {
                                val path = scanner.next()
                                currentDir = when (path) {
                                    ".." -> {
                                        assert(currentDir != null && currentDir?.parent != null) {
                                            "Cannot go up from root directory"
                                        }
                                        currentDir!!.parent
                                    }

                                    "/" -> {
                                        Directory("/").also {
                                            rootDir = it
                                        }
                                    }

                                    else -> {
                                        currentDir!!.getDirectory(path).also { childDir ->
                                            assert(childDir != null) { "Directory $childDir does not exist" }
                                        }
                                    }
                                }

                            }

                            "ls" -> {
                                // no-op
                            }

                            else -> error("Unknown command: $command")
                        }
                    }
                } else {
                    assert(currentDir != null) { "Current directory can't be null" }
                    Scanner(line).use { scanner ->
                        val firstToken = scanner.next()
                        if (firstToken == "dir") {
                            val name = scanner.next()
                            val dir = Directory(name, parent = currentDir)
                            currentDir!!.children.add(dir)
                        } else {
                            val size = firstToken.toInt()
                            val name = scanner.next()
                            val file = File(name, size, parent = currentDir)
                            currentDir!!.children.add(file)
                        }
                    }
                }
            }
        return rootDir!!
    }

    private fun printDir(dir: Directory, indent: Int = 0) {
        print(" ".repeat(indent))
        println("- ${dir.name} (dir)")

        val childIndent = indent + 2
        dir.children.forEach { child ->
            when (child) {
                is Directory -> printDir(child, childIndent)
                is File -> {
                    print(" ".repeat(childIndent))
                    println("- ${child.name} (file, size=${child.size})")
                }
            }
        }
    }

    fun part1(input: String): Int {
        val filesystem = buildFilesystem(input) as Directory

//        printDir(filesystem)

        return filesystem
            .findChildrenRecursively { it is Directory && it.getTotalSize() < 100_000 }
            .map { it as Directory }
            .sumOf { it.getTotalSize() }
    }

    fun part2(input: String): Int {
        return 0
    }
}