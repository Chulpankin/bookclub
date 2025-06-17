package com.itis.bookclub.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.itis.bookclub.domain.model.UserDomainModel
import com.itis.bookclub.domain.repository.UserRepository
import com.itis.bookclub.util.Keys
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : UserRepository {
    override suspend fun signUp(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)

        val userDomainModel = UserDomainModel(
            userId = auth.uid.orEmpty(),
            name = name,
            email = email
        )
        db.collection(Keys.USERS_COLLECTION_KEY)
            .document(auth.uid.orEmpty())
            .set(userDomainModel)
            .await()
    }

    override suspend fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override fun getCurrentUserId(): String = requireNotNull(auth.currentUser?.uid)

    override fun isUserAuthorized(): Boolean = auth.currentUser != null

    override suspend fun getUserCredentials(userId: String): String =
        db.collection(Keys.USERS_COLLECTION_KEY)
            .document(userId)
            .get()
            .await()
            .get(Keys.NAME_KEY) as String
}