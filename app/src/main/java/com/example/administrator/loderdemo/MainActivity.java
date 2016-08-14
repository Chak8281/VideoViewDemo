package com.example.administrator.loderdemo;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private GridView gridView;
    private AdapterS adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new AdapterS(this);
        gridView.setAdapter(adapter);
        // 初始Loader(1. id为0的loader已存在，拿出来直接重用， id为0的loader不存在,触发onCreateLoader方法 )
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE
        };
        return new CursorLoader(
                this,
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, // 视频数据的URI
                projection, null, null, null);
    }

    @Override public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // 将Loader异步加载完成的Cursor，给adapter
        adapter.swapCursor(cursor);
    }

    @Override public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}