package com.example.woofapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.woofapplication.ui.theme.WoofApplicationTheme
import com.example.woofapplication.data.Dog
import com.example.woofapplication.data.dogs
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WoofApplicationTheme{ //sử dụng compose để thiết lập theme cho ui
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    WoofApp()
                }
            }
        }
    }
}
//Hàm compose app tổng hợp gồm top bar và các đối tượng dogitem
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WoofApp() {
    Scaffold(
        topBar = {
            WoofTopAppBar()
        }
    ) { it ->
        LazyColumn(contentPadding = it) {
            items(dogs) {
                DogItem(
                    dog = it,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                )
            }
        }
    }
}
//Hàm compose Tạo top bar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WoofTopAppBar(modifier: Modifier = Modifier){
    CenterAlignedTopAppBar(
        title = {
            Row(
            ) {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                        .padding(dimensionResource(id = R.dimen.padding_small)),
                    painter = painterResource(R.drawable.ic_woof_logo),
                    contentDescription = null
                )
            }
            Text(
                modifier = Modifier
                    .padding(
                        top = 10.dp,
                        start = 60.dp
                    ),
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayLarge
            )
        },
        modifier = modifier
    )
}
//Hàm compose tạo đối tượng dogItem
@Composable
fun DogItem(
    dog: Dog,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    //Tạo 1 biến xepanded gán giá trị false với mục đích xem nó đã mở hobby hay chưa
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    //dùng để thay đổi kích thước nội dung mượt mà
                    animationSpec = spring(
                        //xác định loại hiệu ứng
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        //xác định tham số tỷ lệ giảm dần của đợt nhảy
                        stiffness = Spring.StiffnessMedium
                        //xác định độ cứng của đợt nhảy
                    )
                )
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xffa2d2ff))
                    .padding(dimensionResource(id = R.dimen.padding_small)),
            ) {
                DogIcon(dog.imageResourceId)
                DogInformation(dog.name, dog.age)
                Spacer(modifier = Modifier.weight(1f))
                DogItemButton(expanded = expanded,
                    //gán với expended hiên tại là false
                    onClick = { expanded = !expanded}
                    //Nếu click vào nút thì sẽ gắn expended là true
                )
            }
            if(expanded){
                //nếu expend là true thì sẽ mở hobby
                DogHobby(
                    dog.hobbies,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xffa2d2ff))
                        .padding(
                            start = dimensionResource(R.dimen.padding_medium),
                            top = dimensionResource(R.dimen.padding_small),
                            end = dimensionResource(R.dimen.padding_medium),
                            bottom = dimensionResource(R.dimen.padding_medium)
                        )
                )
            }
        }
    }
}
//Hàm Compose tạo icon
@Composable
fun DogIcon(
    @DrawableRes dogIcon: Int,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .size(dimensionResource(R.dimen.image_size))
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop,
        painter = painterResource(dogIcon),
        contentDescription = null
    )
}
//Hàm compose tạo info
@Composable
fun DogInformation(
    @StringRes dogName: Int,
    dogAge: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(dogName),
            color = Color(0xffffffff),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
        )
        Text(
            text = stringResource(R.string.years_old, dogAge),
            color = Color(0xffffffff),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
//Hàm compose tạo btn để hiện thị hobby
@Composable
private fun DogItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick,
        modifier = modifier
    ) {
        Icon(imageVector = if(expanded)Icons.Filled.ExpandLess else  Icons.Filled.ExpandMore,
            //nếu chưa mở thì gán icon mũi tên xuống dưới còn rồi thì mũi tên lên trên
            contentDescription = stringResource(id = R.string.expand_button_content_description),
            tint = MaterialTheme.colorScheme.secondary
            //Màu của icon
            )
    }
}
//Hàm compose hiện thị hobby của dog
@Composable
fun DogHobby(
    @StringRes dogHobby: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.about),
            color = Color(0xffffffff),
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = stringResource(dogHobby),
            color = Color(0xffffffff),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
//thử nghiệm
@Preview
@Composable
fun WoofPreview() {
    WoofApplicationTheme(darkTheme = false) {
        WoofApp()
    }
}


