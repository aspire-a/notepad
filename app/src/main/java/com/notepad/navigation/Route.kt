package com.notepad.navigation

enum class Route(
    var hasTopBar: Boolean = false,
    val hasFAB: Boolean = false
) {
    ADD,
    DETAIL,
    LIST(hasTopBar = true, hasFAB = true),
    DELETE(hasTopBar = true, hasFAB = true),
    ;

    companion object {
        fun getRoute(routeName: String?): Route? {
            if (routeName != null) {
                values().forEach { route: Route ->
                    if (routeName.equals(route.name, ignoreCase = true)) {
                        return route
                    }
                }
            }
            return null
        }
    }
}

