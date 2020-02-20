package com.aytel.sd.cli

import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Contains info about env.
 */
class Environment {
    private var userDirectory: String = FileSystems.getDefault().getPath(".").toAbsolutePath().normalize().toString()
    private val map: MutableMap<String, String> = HashMap()

    /**
     * Returns a path taking the current working directory into accord.
     */
    fun getDirectory(pathAddition: String=""): Path {
        return Paths.get(userDirectory, pathAddition).toAbsolutePath().normalize()
    }

    /**
     * Changes the current working directory of the interpreter.
     */
    fun setDirectory(newPath: String) {
        userDirectory = newPath;
    }

    /**
     * Resets the current working directory to the original one.
     */
    fun resetDirectory() {
        userDirectory = FileSystems.getDefault().getPath(".").toAbsolutePath().normalize().toString()
    }

    fun get(key: String): String {
        return if (map.containsKey(key)) {
            map.getValue(key)
        } else {
            System.getProperty(key)
        }
    }

    /**
     * Sets values of variable [key] to [value].
     * Works only for this CLI, doesn't really change system env.
     */
    fun set(key: String, value: String) {
        map[key] = value
    }

    /**
     * Returns this env converted to map.
     */
    fun map(): MutableMap<String, String> {
        return map
    }
}