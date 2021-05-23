package com.example.cosu_pra.ConnectFB;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cosu_pra.DTO.Comment;
import com.example.cosu_pra.DTO.Post;
import com.example.cosu_pra.DTO.ProjectPost;
import com.example.cosu_pra.DTO.QnAPost;
import com.example.cosu_pra.DTO.StudyPost;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;

/**
 * HelpPosting 역할
 * 글 올리기 -> 완료
 * 글 지우기 ->완료
 * 글 수정하기 -> 완료
 * 댓글 올리기 -> 완료
 * 댓글 지우기 -> 완료
 * 팀원으로 참여하기 -> 완료, Testing에 예시 있음
 * 팀원으로 참여한것 취소하기 --> 완료
 * 검색기능 -글쓴이 -> 완료
 * 검색기능 -카테고리 -> OR연산으로 계산 가능
 * 검색기능 -내용 -> 불가
 * 신고기능 -> 포스트는 완료
 * 관심 유저 등록하기 -> 유저정보에 저장하면 좋을 듯
 * 관심 글 등록하기 -> 유저정보에 저장하면 좋을 듯
 * <p>
 * 시간 남으면 -> 대댓글, 댓글 신고
 */
public class HelpPosting {
    public static final String PROJECT = "Projects";
    public static final String STUDY = "Studies";
    public static final String QNA = "QnA";
    public static final String COMMENTS = "comments";


    private FirebaseFirestore db;

    public HelpPosting() {
        db = FirebaseFirestore.getInstance();
    }

    /**
     * addPost
     * posting a post
     * --> testing is complete
     *
     * @param collection: path of post, use static final values
     *                    PROJECT, STUDY, QNA
     * @param post:       post to write
     */
    public void addPost(String collection, Post post) {
        db.collection(collection).add(post);
    }

    /**
     * getPost
     * get a post from firebase
     *
     * @param collection: path of post,use static final values
     *                    PROJECT, STUDY, QNA
     * @param postID:     id of post
     * @return Task<DocumentSnapshot>: use addOnSuccessListener method instead Thread
     */
    public Task<DocumentSnapshot> getPost(String collection, String postID) {
        return db.collection(collection).document(postID).get();
    }

    /**
     * deletePost
     * delete a post in collection path which id is postID
     *
     * @param collection: path of post, use static final values
     *                    PROJECT, STUDY, QNA
     * @param postID:     id of post
     */
    public void deletePost(String collection, String postID) {
        // delete sub collection
        db.collection(collection).document(postID)
                .collection(COMMENTS).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                deleteComment(collection, postID, document.getId());
                            }
                        }
                    }
                });

        // delete post
        db.collection(collection).document(postID).delete();
    }

    public void modifyPost(String collection, String postID, Post post) {
        db.collection(collection).document(postID).set(post);
    }

    /**
     * getAllPosts
     * --> test complete
     *
     * @param collection: path of post,use static final values
     *                    PROJECT, STUDY, QNA
     * @return Task<QuerySnapshot>: use addOnCompleteListener method instead Thread
     */
    public Task<QuerySnapshot> getAllPosts(String collection) {
        return db.collection(collection).orderBy("date", Query.Direction.DESCENDING).get();
    }

    /**
     * addComment
     * add a comment on post which id is postID
     *
     * @param collection: path of post,use static final values
     *                    PROJECT, STUDY, QNA
     * @param postID:     id of post
     * @param comment:    comment to add
     */
    public void addComment(String collection, String postID, Comment comment) {

        getPost(collection, postID).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                int numOfComment = 0;
                if (collection.equals(HelpPosting.PROJECT)) {
                    ProjectPost post = documentSnapshot.toObject(ProjectPost.class);
                    numOfComment = post.getComment();
                } else if (collection.equals(HelpPosting.STUDY)) {
                    StudyPost post = documentSnapshot.toObject(StudyPost.class);
                    numOfComment = post.getComment();
                } else if (collection.equals(HelpPosting.QNA)) {
                    QnAPost post = documentSnapshot.toObject(QnAPost.class);
                    numOfComment = post.getComment();
                }
                db.collection(collection).document(postID).collection(COMMENTS).add(comment);
                db.collection(collection).document(postID).update("comment",numOfComment+1);

            }
        });
    }

    /**
     * deleteComment
     * delete a comment
     *
     * @param collection: path of post
     * @param postID:     post id
     * @param commentID:  comment id
     */
    public void deleteComment(String collection, String postID, String commentID) {
        db.collection(collection).document(postID)
                .collection(COMMENTS).document(commentID).delete();
    }

    /**
     * getComments
     * get a task contains comments
     *
     * @param collection: path of post
     * @param postID:     post id
     * @return Task<QuerySnapshot> to use addOnSuccessListener
     */
    public Task<QuerySnapshot> getComments(String collection, String postID) {
        return db.collection(collection).document(postID).collection(COMMENTS).get();
    }

    /**
     * addUser
     * add user to post's member
     *
     * @param collection: path of post(PROJECT or STUDY)
     * @param postID:     post id
     * @param userID:     user id
     */
    public void addUser(String collection, String postID, String userID) {
        DocumentReference docRef = db.collection(collection).document(postID);
        docRef.update("users", FieldValue.arrayUnion(userID));
    }

    /**
     * removeUser
     * remove user in users list
     *
     * @param collection: path of post(PROJECT or STUDY)
     * @param postID:     post id
     * @param userID:     user id to remove
     */
    public void removeUser(String collection, String postID, String userID) {
        DocumentReference docRef = db.collection(collection).document(postID);
        docRef.update("users", FieldValue.arrayRemove(userID));
    }

    /**
     * searchPostByWriter
     * search post which contains search word
     *
     * @param collection: path of post,use static final values(PROJECT, STUDY, QNA)
     * @param writer:     writer
     * @return Task<QuerySnapshot>:
     * use addOnCompleteListener method instead Thread
     */
    public Task<QuerySnapshot> searchPostByWriter(String collection, String writer) {
        return db.collection(collection).whereEqualTo("writer", writer).get();
    }

    /**
     * searchPostByCategory
     * search post by category that contains any value
     *
     * @param collection: path of post(PROJECT, STUDY, QNA)
     * @param category:   String array to use search
     * @return Task<QuerySnapshot>:
     * use addOnCompleteListener method instead Thread
     */
    public Task<QuerySnapshot> searchPostByCategory(String collection, String[] category) {
        return db.collection(collection).whereArrayContainsAny("category", Arrays.asList(category)).get();
    }

    public Task<QuerySnapshot> getReportPost(String collection) {
        return db.collection(collection).whereGreaterThan("report", 0).get();
    }

    public void setReportPostZero(String collection, String postID) {
        db.collection(collection).document(postID).update("report", 0);
    }


}
