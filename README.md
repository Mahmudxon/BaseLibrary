# (!) This repository is no longer supported


# Base Library

Android library for managing multiple stacks of fragments in main contaner. (e.g., open fragments, close)

## Getting Started

This library has BaseFragment and some utils. 

### Prerequisites

* AndroidX
* MinSDK >= 16

## Installing

```
allprojects {
    repositories {
       ...
        maven { url 'https://jitpack.io' }
    }

```

```
dependencies {
       ...
    implementation 'com.github.Mahmudxon:BaseLibrary:3.4'
    }
```

## Using

### Change main_activity.xml file:

```
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/content"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ui.MainActivity" />
```

Note: You must use only FrameLayout in main contaner!!!

### Extend your allFragment from BaseFragment:

```
class ScanFragment : BaseFragment(R.layout.fragment_scan, true /* if you want fragment close by swipe. Default value false */)
```

Call Main fragment in Activity OnCreate Method:

```
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startFragment(MainFragment())
    }
```
### Base Fragment methods for managing fragments:

* openFragment(out BaseFragment) - open your fragment
* addFragment() - open Fragment you control animation and you can send senderData
* startFragment() - replace Fragment you control animation and you can send senderData
* replaceAll() - close all Fragments open this
