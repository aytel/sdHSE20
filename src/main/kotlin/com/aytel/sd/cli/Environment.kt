package com.aytel.sd.cli

class Environment {
    private val map: MutableMap<String, String> = HashMap()

    fun get(key: String): String {
        return if (map.containsKey(key)) {
            map.getValue(key)
        } else {
            System.getProperty(key)
        }
    }

    fun set(key: String, value: String) {
        map[key] = value
    }

    fun map(): Map<String, String> {
        return map
    }
}