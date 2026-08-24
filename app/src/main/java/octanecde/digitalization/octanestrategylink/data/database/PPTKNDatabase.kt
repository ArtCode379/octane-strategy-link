package octanecde.digitalization.octanestrategylink.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import octanecde.digitalization.octanestrategylink.data.dao.BookingDao
import octanecde.digitalization.octanestrategylink.data.database.converter.Converters
import octanecde.digitalization.octanestrategylink.data.entity.BookingEntity

@Database(
    entities = [BookingEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PPTKNDatabase : RoomDatabase() {

    abstract fun bookingDao(): BookingDao
}

