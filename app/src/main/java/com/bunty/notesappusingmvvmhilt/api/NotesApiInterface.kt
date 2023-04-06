package com.bunty.notesappusingmvvmhilt.api

import com.bunty.notesappusingmvvmhilt.models.NoteRequest
import com.bunty.notesappusingmvvmhilt.models.NoteResponse
import retrofit2.Response
import retrofit2.http.*

interface NotesApiInterface {

    @POST("/note")
    suspend fun createNote(@Body noteRequest: NoteRequest): Response<NoteResponse>

    @GET("/note")
    suspend fun getNotes(): Response<List<NoteResponse>>

    @DELETE("/note/{noteId}")
    suspend fun deleteNote(@Path("noteId") noteId: String): Response<NoteResponse>

    @PUT("/note/{noteId}")
    suspend fun updateNote(
        @Path("noteId") noteId: String,
        @Body noteRequest: NoteRequest
    ): Response<NoteResponse>

}