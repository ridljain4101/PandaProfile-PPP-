package com.example.dogprofilepage2

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

@Composable
fun ProfilePage(){
  Card(elevation = 6.dp,modifier = Modifier
      .padding(top = 100.dp, bottom = 100.dp, start = 30.dp, end = 30.dp)
      .fillMaxSize()
      .border(width = 6.dp, color = Color.Black, shape = RoundedCornerShape(4.dp)) )
  {

      BoxWithConstraints() {
          val constraints = if (minWidth < 550.dp) {
              potraitConstraint(margin = 16.dp)
          } else {
              // * TODO LANDSCAPE MODE
        landscapeConstraints(margin = 16.dp)
          }


      // below - for having the scrolling in the card when we rotate the phone, we use
      // verticalScroll and hence the vertical Arrangement.rememberScrollState means that
      // we remember the scrolling state that we had before the rotation

      // Column - layout composable which allows to put items inside it
      ConstraintLayout(constraints) {

// val (image,nameText,countryText,rowStat,followButton,messageButton) = createRefs()
//        //   val guideline = createGuidelineFromTop(0.1f)
          //** helps in shifting all the objects by this fraction downwards starting from the image
          //** different from margin as margin only shifts that particular object to which it
          //## is applied

          Image(painter = painterResource(id = R.drawable.panda), contentDescription = "Cute panda",
              modifier = Modifier
                  .size(200.dp)
                  .clip(CircleShape) // to modify the image
                  .border(width = 3.dp, color = Color.Cyan, shape = CircleShape)
                  .layoutId("image"),
              contentScale = ContentScale.Crop // to fit the image to the circle shape perfectly
          )
          // image is a composable which displays pictures
          // has two comp. components painter - resource
          // contentDescri - description
          Text(text = "Chinese Panda", fontWeight = FontWeight.Bold,
              modifier = Modifier.layoutId("nameText"))

          Text(text = "China", modifier = Modifier.layoutId("countryText"))



          Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
              .fillMaxSize()
              .padding(18.dp)
              .layoutId("rowStat")
          )
          {
// elements or components of row are mentioned here
              ProfileStats(count = "150", text = "Followers")
              ProfileStats(count = "100", text = "Following")
              ProfileStats(count = "30", text = "Posts")


          }



          Button(onClick = { /*TODO*/ }, // onClick is a subcomposable of the Button composable

              modifier = Modifier.layoutId("followButton")) {
              Text(text = "Follow User") // notice no , here before the text composbale for the button
          }
          Button(onClick = { /*TODO*/ },
              modifier = Modifier.layoutId("messageButton")
          ) {
              Text(text = "Send Message")
          }
      }
  }
    } //** end of the box with constraints
}


private fun potraitConstraint(margin: Dp) : ConstraintSet{
    return ConstraintSet {
        val image = createRefFor("image")
        val nameText = createRefFor("nameText")
        val countryText = createRefFor("countryText")
        val rowStat = createRefFor("rowStat")
        val followButton= createRefFor("followButton")
        val messageButton = createRefFor("messageButton")
        val guideLine = createGuidelineFromTop(0.1f)



//* How to use constrain

        constrain(image){
            top.linkTo(guideLine)
            end.linkTo(parent.end)
            start.linkTo(parent.start)
        }
        constrain(nameText){
            top.linkTo(image.bottom)
            end.linkTo(parent.end)
            start.linkTo(parent.start)
        }
        constrain(countryText){
            top.linkTo(nameText.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(rowStat){
            top.linkTo(countryText.bottom)
        }
        constrain(followButton){
            top.linkTo(rowStat.bottom, margin = margin) // this is the top margin

            start.linkTo(parent.start)
            end.linkTo(messageButton.start)
            width = Dimension.wrapContent
        }
        constrain(messageButton){
            top.linkTo(rowStat.bottom, margin = margin) // this is the top margin

            start.linkTo(followButton.end)
            end.linkTo(parent.end)
            width = Dimension.wrapContent
        }
 //** Use wrap content for the buttons
    }
}


private fun landscapeConstraints(margin: Dp) : ConstraintSet{
    return ConstraintSet {
        val image = createRefFor("image")
        val nameText = createRefFor("nameText")
        val countryText = createRefFor("countryText")
        val rowStat = createRefFor("rowStat")
        val followButton= createRefFor("followButton")
        val messageButton = createRefFor("messageButton")
        val guideLine = createGuidelineFromTop(0.3f)

    constrain(image){
        top.linkTo(parent.top, margin = margin)
        start.linkTo(parent.start, margin = margin)
    }
        constrain(nameText){
            top.linkTo(image.bottom)
            start.linkTo(image.start)
        }
        constrain(countryText){
            top.linkTo(nameText.bottom)
            start.linkTo(nameText.start)
            end.linkTo(nameText.end)
        }
        constrain(rowStat){
            top.linkTo(image.top)
            start.linkTo(image.end,margin = margin)
            end.linkTo(parent.end)
        }
        constrain(followButton){
            top.linkTo(rowStat.bottom,margin = 16.dp)
            start.linkTo(rowStat.start)
            end.linkTo(messageButton.start)
            bottom.linkTo(countryText.bottom)
       width = Dimension.wrapContent
        }

        constrain(followButton){
            top.linkTo(rowStat.bottom,margin = 16.dp)
            start.linkTo(followButton.end)
            end.linkTo(parent.end)
            bottom.linkTo(countryText.bottom)
            width = Dimension.wrapContent
        }









    }
}


@Composable
fun ProfileStats(count:String , text:String) {
// we created this function in order to avoid repetition of same code and
    // simplify it
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = count, fontWeight = FontWeight.Bold) // for the numbers line
        Text(text = text)// for the below part
    }

}

@Preview(showBackground = true)
@Composable
fun ProfilePagePreview(){
    ProfilePage()
}
