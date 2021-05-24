package com.example.cosu_pra;

import androidx.core.content.ContextCompat;

import com.example.cosu_pra.Adapter.ChatRoomAdatper;
import com.example.cosu_pra.Adapter.ChatRoomItem;
import com.example.cosu_pra.Adapter.MyCommentItemAdapter;
import com.example.cosu_pra.Adapter.MylikeItemAdapter;
import com.example.cosu_pra.Adapter.MypostItemAdapter;
import com.example.cosu_pra.Adapter.PostCategoryAdapter;
import com.example.cosu_pra.Adapter.PostCategoryItem;
import com.example.cosu_pra.Adapter.PostListAdapter;
import com.example.cosu_pra.Adapter.PostListItem;
import com.example.cosu_pra.Adapter.ReportAdatper;
import com.example.cosu_pra.Adapter.ReportListItem;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_chatroomAdapter() {
        ChatRoomAdatper adtper = new ChatRoomAdatper();
        ChatRoomItem item = new ChatRoomItem();
        item.setLastMSG("hello");
        adtper.addItem(item);

        assertEquals(1, adtper.getCount());
        assertEquals(item, adtper.getItem(0));
        assertEquals("hello", adtper.getItem(0).getLastMSG());
    }

    @Test
    public void test_commentAdapter() {
        MyCommentItemAdapter adtper = new MyCommentItemAdapter();
        CommentItem item = new CommentItem("hello","writer1");

        adtper.addItem(item);

        assertEquals(1, adtper.getCount());
        assertEquals(item, adtper.getItem(0));

    }

    @Test
    public void test_mylikeAdapter() {
        MylikeItemAdapter adtper = new MylikeItemAdapter();
        LikeItem item = new LikeItem("hello","writer1");

        adtper.addItem(item);

        assertEquals(1, adtper.getCount());
        assertEquals(item, adtper.getItem(0));
    }

    @Test
    public void test_mypostAdapter() {
        MypostItemAdapter adtper = new MypostItemAdapter();
        MyPostItem item = new MyPostItem("hello","AI");

        adtper.addItem(item);

        assertEquals(1, adtper.getCount());
        assertEquals(item, adtper.getItem(0));
        assertEquals("AI", adtper.getItem(0).getCategory());
        assertEquals("hello", adtper.getItem(0).getContent());
    }

    @Test
    public void test_postListAdapter() {
        PostListAdapter adtper = new PostListAdapter();
        PostListItem item = new PostListItem();
        item.setComment("comment");
        item.setDate("2010");
        item.setGood("3");
        item.setPeople("4");
        item.setPostID("sdfe");
        item.setTitle("title");
        adtper.addItem(item);

        assertEquals(1, adtper.getCount());
        assertEquals(item, adtper.getItem(0));
        assertEquals("comment", adtper.getItem(0).getComment());
        assertEquals("2010", adtper.getItem(0).getDate());
        assertEquals("3", adtper.getItem(0).getGood());
        assertEquals("4", adtper.getItem(0).getPeople());
        assertEquals("sdfe", adtper.getItem(0).getPostID());
        assertEquals("title", adtper.getItem(0).getTitle());
    }

    @Test
    public void test_reportAdapter() {
        ReportAdatper adtper = new ReportAdatper();
        ReportListItem item = new ReportListItem("hello","AI");

        adtper.addItem(item);

        assertEquals(1, adtper.getCount());
        assertEquals(item, adtper.getItem(0));
    }
}