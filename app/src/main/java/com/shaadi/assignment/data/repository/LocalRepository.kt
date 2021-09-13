package com.shaadi.assignment.data.repository

import com.shaadi.assignment.data.db.ShaadiMatchDao
import com.shaadi.assignment.data.model.local.ShaadiMatch
import com.shaadi.assignment.data.model.remote.MatchStatus
import com.shaadi.assignment.utils.STATUS_ACCEPT
import com.shaadi.assignment.utils.STATUS_DECLINE
import javax.inject.Inject

class LocalRepository @Inject constructor(private val dao: ShaadiMatchDao) {

    suspend fun updateMatchStatus(shaadiMatch: ShaadiMatch){
        dao.insert(shaadiMatch)
    }
    suspend fun getMatchById(id: String): MatchStatus {
        val shaadiMatch = dao.getShaadiStatusById(id)
        return when(shaadiMatch?.status){
            STATUS_ACCEPT -> MatchStatus.ACCEPT
            STATUS_DECLINE -> MatchStatus.DECLINE
            else -> MatchStatus.NONE
        }
    }
}