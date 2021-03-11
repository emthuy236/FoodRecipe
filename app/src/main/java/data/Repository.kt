package data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource:RemoteDataSource
) {
    val remote = remoteDataSource
}