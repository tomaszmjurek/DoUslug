package pp.inzynierka.douslug.test

import pp.inzynierka.douslug.model.Visit

sealed class RecyclerItem {
    data class WeekVisit(val visit: Visit): RecyclerItem()
    data class Section(val title: String): RecyclerItem()
}