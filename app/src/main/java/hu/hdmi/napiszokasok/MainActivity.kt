package hu.hdmi.napiszokasok
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import hu.hdmi.napiszokasok.ui.theme.NapiSzokasokTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
             NapiSzokasokTheme{
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainComposable(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

class DailyHabits(val text: String, isChecked: Boolean = false) {
    var isChecked by mutableStateOf(isChecked)
}

@Composable
fun MainComposable(modifier: Modifier = Modifier){
    val mainList = remember { mutableStateListOf<DailyHabits>(DailyHabits("Megittam 2 liter vizet"), DailyHabits("Futottam 2km-t"), DailyHabits("Utolérni Merényit Clash Royale-ban")) }
    var newGoalText by remember { mutableStateOf("") }

    Text(text = "Daily Habits List ", modifier = Modifier.padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 8.dp), fontSize = 28.sp, lineHeight = 28.sp)
    Column(modifier = modifier.padding(top = 32.dp, start = 8.dp, end = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        if (mainList.isNotEmpty()) {
            Text(text = "Habits: ${mainList.size} items", modifier = Modifier.padding(bottom = 8.dp), fontSize = 18.sp, lineHeight = 18.sp)
            LazyColumn(modifier = Modifier) {
                itemsIndexed(mainList) { _, item ->
                    LazyRow(verticalAlignment = Alignment.CenterVertically) {
                        item{
                            Checkbox(checked = item.isChecked, onCheckedChange = { checked ->
                                item.isChecked = checked
                            })
                            Text(text = if (item.isChecked) "${item.text} ✅" else item.text, style = TextStyle(textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None), fontSize = 18.sp, lineHeight = 18.sp )
                            IconButton(onClick = {
                                mainList.remove(item)
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                        }
                    }
                }
            }
        }
        else {
            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "No items to show", modifier = Modifier.padding(16.dp), fontSize = 18.sp, lineHeight = 18.sp,)
            }
        }
    }
    Row(modifier = Modifier.fillMaxSize().padding(bottom = 64.dp, end = 8.dp), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.Bottom) {
        TextField(
            value = newGoalText,
            onValueChange = { newGoalText = it },
            label = { Text("Habit details") },
            modifier = Modifier
                .padding(16.dp)
        )
        IconButton(
            onClick = {
                if (newGoalText.isNotEmpty()) {
                    mainList.add(DailyHabits(newGoalText))
                    newGoalText = ""
                } },
            modifier = Modifier
                .padding(16.dp)
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.Blue)
        ) {
            Text(text = "Add", color = Color.White)
        }
    }
}