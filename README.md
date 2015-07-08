StikkyHeader
============

This is a very simple library for Android that allows you to stick an header to a ListView and easily apply animation to it

## Usage

To use the StikkyHeader library, you just need 3 lines:

```java
  StikkyHeaderBuilder.stickTo(mListView)
    .setHeader(R.id.header, containerLayout)
    .minHeightHeader(250)
    .build();
```
that's all, folks! 

## Header Animator

Using the StikkyHeader you can create easly some nice animations extending the ``HeaderStikkyAnimator`` and using the utility ``AnimatorBuilder``.
The animations available are ``Translation``, ``Scale`` and ``Fade`` and can be combined to build an animation during the translation of the StikkyHeader.

Example:
```java
public class IconAnimator extends HeaderStikkyAnimator {

    @Override
    public AnimatorBuilder getAnimatorBuilder() {

        View viewToAnimate = getHeader().findViewById(R.id.icon);
        Point point = new Point(50,100) // translate to the point with coordinate (50,100);
        float scaleX = 0.5f //scale to the 50%
        float scaleY = 0.5f //scale to the 50%
        float fade = 0.2f // 20% fade

        AnimatorBuilder animatorBuilder = AnimatorBuilder.create()
            .applyScale(viewToAnimate, scaleX, scaleY)
            .applyTranslation(viewToAnimate, point)
            .applyFade(viewToAnimate, fade);

        return animatorBuilder;
    }
}
```

and then set the animator to the StikkyHeader:

```java
  StikkyHeaderBuilder.stickTo(mListView)
    .setHeader(R.id.header, containerLayout)
    .minHeightHeader(250)
    .animator(new IconAnimator())
    .build();
```

## ViewGroups supported

The StikkyHeader supports:
- ListView
- RecyclerView
- ScrollView

## How to integrate

Grab via Gradle:
```groovy
repositories {
  maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
}
dependencies {
  compile 'com.github.carlonzo.stikkyheader:core:0.0.3-SNAPSHOT'
}
```

or via Maven:
```xml
<dependency>
  <groupId>com.github.carlonzo.stikkyheader</groupId>
  <artifactId>core</artifactId>
  <version>0.0.3-SNAPSHOT</version>
</dependency>
```

## Video

![Example 1](https://raw.githubusercontent.com/carlonzo/StikkyHeader/develop/readme/example1.gif)
![Example 2](https://raw.githubusercontent.com/carlonzo/StikkyHeader/develop/readme/example2.gif)

## Demo

here the [link][1] to the emulator to try few examples.
[1]: https://appetize.io/embed/urptay0fdprc9dp3xzk6e41g70?screenOnly=false&scale=75
