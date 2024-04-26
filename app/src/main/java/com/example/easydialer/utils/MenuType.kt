package com.example.easydialer.utils

enum class MenuType(val displayName: String) {
    TELE_CALLER("Tele Caller"),
    DATA_ENTRY("Data entry"),
    ATTENDANCE("Attendance"),
    TICKET_SYSTEM("Ticket System"),
    CRM("CRM"),
    LEAVE("Leave"),
    OTHER_OPTION("Other Option");

    companion object {
        fun fromDisplayName(displayName: String): MenuType? {
            return entries.firstOrNull { it.displayName.equals(displayName, ignoreCase = true) }
        }
    }
}