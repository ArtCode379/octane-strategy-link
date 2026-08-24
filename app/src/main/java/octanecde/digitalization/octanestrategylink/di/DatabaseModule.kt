package octanecde.digitalization.octanestrategylink.di

import androidx.room.Room
import octanecde.digitalization.octanestrategylink.data.database.PPTKNDatabase
import org.koin.dsl.module

private const val DB_NAME = "pptkn_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = PPTKNDatabase::class.java,
        name = DB_NAME
        ).build()
    }

    single { get<PPTKNDatabase>().bookingDao()}

}