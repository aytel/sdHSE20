package com.aytel.sd.cli

/**
 * Contains info about env.
 */
class Environment {
    private val map: MutableMap<String, String> = HashMap()

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