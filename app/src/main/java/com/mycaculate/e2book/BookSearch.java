package com.mycaculate.e2book;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;

public class BookSearch {
    private int bookId, price, catalogId, shelveId, ownerId, receiverId;
    private String bookName, catalog, author, publisher, bookNotes, bookTime,
            shelveNotes, shelveTime, transactionTime, dismountedTime,
            ownerAccount, ownerName,
            receiverAccount, receiverName;
    public static final String V_BOOK_SHELVES_BOOK_ID="book_id";
    public static final String V_BOOK_SHELVES_PRICE="price";
    public static final String V_BOOK_SHELVES_CATALOG_ID="catalog_id";
    public static final String V_BOOK_SHELVES_SHELVE_ID="shelve_id";
    public static final String V_BOOK_SHELVES_OWNER_ID="owner_id";
    public static final String V_BOOK_SHELVES_RECEIVER_ID="receiver_id";
    public static final String V_BOOK_SHELVES_BOOK_NAME="book_name";
    public static final String V_BOOK_SHELVES_CATALOG="catalog";
    public static final String V_BOOK_SHELVES_AUTHOR="author";
    public static final String V_BOOK_SHELVES_PUBLISHER="publisher";
    public static final String V_BOOK_SHELVES_BOOK_NOTES="book_notes";
    public static final String V_BOOK_SHELVES_BOOK_TIME="book_time";
    public static final String V_BOOK_SHELVES_SHELVE_NOTES="shelve_notes";
    public static final String V_BOOK_SHELVES_SHELVE_TIME="shelve_time";
    public static final String V_BOOK_SHELVES_TRANSACTION_TIME="transaction_time";
    public static final String V_BOOK_SHELVES_DISMOUNTED_TIME="dismounted_time";
    public static final String V_BOOK_SHELVES_OWNER_ACCOUNT="owner_account";
    public static final String V_BOOK_SHELVES_OWNER_NAME="owner_name";
    public static final String V_BOOK_SHELVES_RECEIVER_ACCOUNT="receiver_account";
    public static final String V_BOOK_SHELVES_RECEIVER_NAME="receiver_name";

    public BookSearch(int bookId, String bookName, int catalogId, String catalog,  String author, String publisher, int price, String bookNotes, String bookTime,
                      int shelveId, String shelveNotes, String shelveTime, String transactionTime, String dismountedTime,
                      int ownerId, String ownerAccount, String ownerName,
                      int receiverId, String receiverAccount, String receiverName)
    {
        this.bookId = bookId;
        this.bookName = bookName;
        this.catalogId = catalogId;
        this.catalog = catalog;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.bookNotes = bookNotes;
        this.bookTime = bookTime;
        this.shelveId = shelveId;
        this.shelveNotes = shelveNotes;
        this.shelveTime = shelveTime;
        this.transactionTime = transactionTime;
        this.dismountedTime = dismountedTime;
        this.ownerId = ownerId;
        this.ownerAccount = ownerAccount;
        this.ownerName = ownerName;
        this.receiverId = receiverId;
        this.receiverAccount = receiverAccount;
        this.receiverName = receiverName;
    }

    public int getBookId() {
        return bookId;
    }

    public int getPrice() {
        return price;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public int getShelveId() {
        return shelveId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getBookNotes() {
        return bookNotes;
    }

    public String getBookTime() {
        return bookTime;
    }

    public String getShelveNotes() {
        return shelveNotes;
    }

    public String getShelveTime() {
        return shelveTime;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public String getDismountedTime() {
        return dismountedTime;
    }

    public String getOwnerAccount() {
        return ownerAccount;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getReceiverAccount() {
        return receiverAccount;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public static BookSearch convertBookSearch(JSONObject obj) throws JSONException {
        int bookId=obj.getInt(BookSearch.V_BOOK_SHELVES_BOOK_ID);
        String bookName=obj.getString(BookSearch.V_BOOK_SHELVES_BOOK_NAME);
        int catalogId=obj.getInt(BookSearch.V_BOOK_SHELVES_CATALOG_ID);
        String catalog=obj.getString(BookSearch.V_BOOK_SHELVES_CATALOG);
        String author=obj.getString(BookSearch.V_BOOK_SHELVES_AUTHOR);
        String publisher=obj.getString(BookSearch.V_BOOK_SHELVES_PUBLISHER);
        int price=obj.getInt(BookSearch.V_BOOK_SHELVES_PRICE);
        String bookNotes=obj.getString(BookSearch.V_BOOK_SHELVES_BOOK_NOTES);
        String bookTime=obj.getString(BookSearch.V_BOOK_SHELVES_BOOK_TIME);
        int shelveId=obj.getInt(BookSearch.V_BOOK_SHELVES_SHELVE_ID);
        String shelveNotes=obj.getString(BookSearch.V_BOOK_SHELVES_SHELVE_NOTES);
        String shelveTime=obj.getString(BookSearch.V_BOOK_SHELVES_SHELVE_TIME);
        String transactionTime=obj.getString(BookSearch.V_BOOK_SHELVES_TRANSACTION_TIME);
        String dismountedTime=obj.getString(BookSearch.V_BOOK_SHELVES_DISMOUNTED_TIME);
        int ownerId=obj.getInt(BookSearch.V_BOOK_SHELVES_OWNER_ID);
        String ownerAccount=obj.getString(BookSearch.V_BOOK_SHELVES_OWNER_ACCOUNT);
        String ownerName=obj.getString(BookSearch.V_BOOK_SHELVES_OWNER_NAME);
        int receiverId=obj.getInt(BookSearch.V_BOOK_SHELVES_RECEIVER_ID);
        String receiverAccount=obj.getString(BookSearch.V_BOOK_SHELVES_RECEIVER_ACCOUNT);
        String receiverName=obj.getString(BookSearch.V_BOOK_SHELVES_RECEIVER_NAME);
        Log.v("jsonObj=",obj.getString(BookSearch.V_BOOK_SHELVES_BOOK_NAME).toString());
        return new BookSearch(bookId, bookName, catalogId, catalog,  author, publisher, price, bookNotes, bookTime,
                shelveId, shelveNotes, shelveTime, transactionTime, dismountedTime,
                ownerId, ownerAccount, ownerName,
                receiverId, receiverAccount, receiverName);
    }
}
