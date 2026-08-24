package octanecde.digitalization.octanestrategylink.ui.composable.screen.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    SettingsScreenContent(
        modifier = modifier,
        onOpenSupport = {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://octanecde.surf")))
        },
        onOpenLegal = {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://octanecde.surf/privacy")))
        },
    )
}

@Composable
fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    onOpenSupport: () -> Unit,
    onOpenLegal: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("About", style = MaterialTheme.typography.headlineMedium)
        Card(modifier = Modifier.fillMaxWidth()) {
            SettingRow(Icons.Default.Business, "Company", "OCTANE CDE LIMITED")
            HorizontalDivider()
            SettingRow(Icons.Default.Verified, "App version", "1.0.0")
        }
        Text("Legal", style = MaterialTheme.typography.titleMedium)
        Button(
            onClick = onOpenLegal,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Privacy & Terms")
        }
        Text("Support", style = MaterialTheme.typography.titleMedium)
        Text(
            "Questions about a consultation or an existing session? Our team is ready to help.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Button(
            onClick = onOpenSupport,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(Icons.Default.Language, null)
            Text("  Customer Support")
        }
        Text("Opens octanecde.surf in your browser.", style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun SettingRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Icon(icon, null, tint = MaterialTheme.colorScheme.primary)
        Column {
            Text(label, style = MaterialTheme.typography.labelLarge)
            Text(value, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
