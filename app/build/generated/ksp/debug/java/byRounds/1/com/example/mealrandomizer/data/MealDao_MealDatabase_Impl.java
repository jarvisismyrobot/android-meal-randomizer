package com.example.mealrandomizer.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MealDao_MealDatabase_Impl implements MealDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Meal> __insertionAdapterOfMeal;

  private final EntityDeletionOrUpdateAdapter<Meal> __deletionAdapterOfMeal;

  private final EntityDeletionOrUpdateAdapter<Meal> __updateAdapterOfMeal;

  public MealDao_MealDatabase_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMeal = new EntityInsertionAdapter<Meal>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `meals` (`id`,`name`,`description`,`difficulty`,`cookingTimeMinutes`,`calories`,`categories`,`createdAt`,`lastGenerated`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Meal entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getDescription());
        final String _tmp = Converters.INSTANCE.fromDifficulty(entity.getDifficulty());
        statement.bindString(4, _tmp);
        statement.bindLong(5, entity.getCookingTimeMinutes());
        if (entity.getCalories() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getCalories());
        }
        final String _tmp_1 = Converters.INSTANCE.fromCategories(entity.getCategories());
        statement.bindString(7, _tmp_1);
        final Long _tmp_2 = Converters.INSTANCE.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_2 == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp_2);
        }
        final Long _tmp_3 = Converters.INSTANCE.dateToTimestamp(entity.getLastGenerated());
        if (_tmp_3 == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, _tmp_3);
        }
      }
    };
    this.__deletionAdapterOfMeal = new EntityDeletionOrUpdateAdapter<Meal>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `meals` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Meal entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfMeal = new EntityDeletionOrUpdateAdapter<Meal>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `meals` SET `id` = ?,`name` = ?,`description` = ?,`difficulty` = ?,`cookingTimeMinutes` = ?,`calories` = ?,`categories` = ?,`createdAt` = ?,`lastGenerated` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Meal entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getDescription());
        final String _tmp = Converters.INSTANCE.fromDifficulty(entity.getDifficulty());
        statement.bindString(4, _tmp);
        statement.bindLong(5, entity.getCookingTimeMinutes());
        if (entity.getCalories() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getCalories());
        }
        final String _tmp_1 = Converters.INSTANCE.fromCategories(entity.getCategories());
        statement.bindString(7, _tmp_1);
        final Long _tmp_2 = Converters.INSTANCE.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_2 == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp_2);
        }
        final Long _tmp_3 = Converters.INSTANCE.dateToTimestamp(entity.getLastGenerated());
        if (_tmp_3 == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, _tmp_3);
        }
        statement.bindLong(10, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final Meal meal, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfMeal.insertAndReturnId(meal);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Meal meal, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfMeal.handle(meal);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Meal meal, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfMeal.handle(meal);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Meal>> getAll() {
    final String _sql = "SELECT * FROM meals ORDER BY name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"meals"}, new Callable<List<Meal>>() {
      @Override
      @NonNull
      public List<Meal> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfCookingTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "cookingTimeMinutes");
          final int _cursorIndexOfCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "calories");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastGenerated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastGenerated");
          final List<Meal> _result = new ArrayList<Meal>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Meal _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final Difficulty _tmpDifficulty;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfDifficulty);
            _tmpDifficulty = Converters.INSTANCE.toDifficulty(_tmp);
            final int _tmpCookingTimeMinutes;
            _tmpCookingTimeMinutes = _cursor.getInt(_cursorIndexOfCookingTimeMinutes);
            final Integer _tmpCalories;
            if (_cursor.isNull(_cursorIndexOfCalories)) {
              _tmpCalories = null;
            } else {
              _tmpCalories = _cursor.getInt(_cursorIndexOfCalories);
            }
            final List<Category> _tmpCategories;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = Converters.INSTANCE.toCategories(_tmp_1);
            final Date _tmpCreatedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_3 = Converters.INSTANCE.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_3;
            }
            final Date _tmpLastGenerated;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfLastGenerated)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfLastGenerated);
            }
            _tmpLastGenerated = Converters.INSTANCE.fromTimestamp(_tmp_4);
            _item = new Meal(_tmpId,_tmpName,_tmpDescription,_tmpDifficulty,_tmpCookingTimeMinutes,_tmpCalories,_tmpCategories,_tmpCreatedAt,_tmpLastGenerated);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getById(final long id, final Continuation<? super Meal> $completion) {
    final String _sql = "SELECT * FROM meals WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Meal>() {
      @Override
      @Nullable
      public Meal call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfCookingTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "cookingTimeMinutes");
          final int _cursorIndexOfCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "calories");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastGenerated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastGenerated");
          final Meal _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final Difficulty _tmpDifficulty;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfDifficulty);
            _tmpDifficulty = Converters.INSTANCE.toDifficulty(_tmp);
            final int _tmpCookingTimeMinutes;
            _tmpCookingTimeMinutes = _cursor.getInt(_cursorIndexOfCookingTimeMinutes);
            final Integer _tmpCalories;
            if (_cursor.isNull(_cursorIndexOfCalories)) {
              _tmpCalories = null;
            } else {
              _tmpCalories = _cursor.getInt(_cursorIndexOfCalories);
            }
            final List<Category> _tmpCategories;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = Converters.INSTANCE.toCategories(_tmp_1);
            final Date _tmpCreatedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_3 = Converters.INSTANCE.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_3;
            }
            final Date _tmpLastGenerated;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfLastGenerated)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfLastGenerated);
            }
            _tmpLastGenerated = Converters.INSTANCE.fromTimestamp(_tmp_4);
            _result = new Meal(_tmpId,_tmpName,_tmpDescription,_tmpDifficulty,_tmpCookingTimeMinutes,_tmpCalories,_tmpCategories,_tmpCreatedAt,_tmpLastGenerated);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Meal>> getByCategory(final Category category) {
    final String _sql = "SELECT * FROM meals WHERE categories LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = Converters.INSTANCE.fromCategory(category);
    _statement.bindString(_argIndex, _tmp);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"meals"}, new Callable<List<Meal>>() {
      @Override
      @NonNull
      public List<Meal> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfCookingTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "cookingTimeMinutes");
          final int _cursorIndexOfCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "calories");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastGenerated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastGenerated");
          final List<Meal> _result = new ArrayList<Meal>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Meal _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final Difficulty _tmpDifficulty;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfDifficulty);
            _tmpDifficulty = Converters.INSTANCE.toDifficulty(_tmp_1);
            final int _tmpCookingTimeMinutes;
            _tmpCookingTimeMinutes = _cursor.getInt(_cursorIndexOfCookingTimeMinutes);
            final Integer _tmpCalories;
            if (_cursor.isNull(_cursorIndexOfCalories)) {
              _tmpCalories = null;
            } else {
              _tmpCalories = _cursor.getInt(_cursorIndexOfCalories);
            }
            final List<Category> _tmpCategories;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = Converters.INSTANCE.toCategories(_tmp_2);
            final Date _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_4 = Converters.INSTANCE.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_4;
            }
            final Date _tmpLastGenerated;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfLastGenerated)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfLastGenerated);
            }
            _tmpLastGenerated = Converters.INSTANCE.fromTimestamp(_tmp_5);
            _item = new Meal(_tmpId,_tmpName,_tmpDescription,_tmpDifficulty,_tmpCookingTimeMinutes,_tmpCalories,_tmpCategories,_tmpCreatedAt,_tmpLastGenerated);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Meal>> search(final String query) {
    final String _sql = "SELECT * FROM meals WHERE name LIKE '%' || ? || '%' OR description LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"meals"}, new Callable<List<Meal>>() {
      @Override
      @NonNull
      public List<Meal> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfCookingTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "cookingTimeMinutes");
          final int _cursorIndexOfCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "calories");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastGenerated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastGenerated");
          final List<Meal> _result = new ArrayList<Meal>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Meal _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final Difficulty _tmpDifficulty;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfDifficulty);
            _tmpDifficulty = Converters.INSTANCE.toDifficulty(_tmp);
            final int _tmpCookingTimeMinutes;
            _tmpCookingTimeMinutes = _cursor.getInt(_cursorIndexOfCookingTimeMinutes);
            final Integer _tmpCalories;
            if (_cursor.isNull(_cursorIndexOfCalories)) {
              _tmpCalories = null;
            } else {
              _tmpCalories = _cursor.getInt(_cursorIndexOfCalories);
            }
            final List<Category> _tmpCategories;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = Converters.INSTANCE.toCategories(_tmp_1);
            final Date _tmpCreatedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_3 = Converters.INSTANCE.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_3;
            }
            final Date _tmpLastGenerated;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfLastGenerated)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfLastGenerated);
            }
            _tmpLastGenerated = Converters.INSTANCE.fromTimestamp(_tmp_4);
            _item = new Meal(_tmpId,_tmpName,_tmpDescription,_tmpDifficulty,_tmpCookingTimeMinutes,_tmpCalories,_tmpCategories,_tmpCreatedAt,_tmpLastGenerated);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getRandom(final Continuation<? super Meal> $completion) {
    final String _sql = "SELECT * FROM meals ORDER BY RANDOM() LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Meal>() {
      @Override
      @Nullable
      public Meal call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfCookingTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "cookingTimeMinutes");
          final int _cursorIndexOfCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "calories");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastGenerated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastGenerated");
          final Meal _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final Difficulty _tmpDifficulty;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfDifficulty);
            _tmpDifficulty = Converters.INSTANCE.toDifficulty(_tmp);
            final int _tmpCookingTimeMinutes;
            _tmpCookingTimeMinutes = _cursor.getInt(_cursorIndexOfCookingTimeMinutes);
            final Integer _tmpCalories;
            if (_cursor.isNull(_cursorIndexOfCalories)) {
              _tmpCalories = null;
            } else {
              _tmpCalories = _cursor.getInt(_cursorIndexOfCalories);
            }
            final List<Category> _tmpCategories;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = Converters.INSTANCE.toCategories(_tmp_1);
            final Date _tmpCreatedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_3 = Converters.INSTANCE.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_3;
            }
            final Date _tmpLastGenerated;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfLastGenerated)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfLastGenerated);
            }
            _tmpLastGenerated = Converters.INSTANCE.fromTimestamp(_tmp_4);
            _result = new Meal(_tmpId,_tmpName,_tmpDescription,_tmpDifficulty,_tmpCookingTimeMinutes,_tmpCalories,_tmpCategories,_tmpCreatedAt,_tmpLastGenerated);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getRandomExcluding(final List<Long> excludedIds,
      final Continuation<? super Meal> $completion) {
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT * FROM meals WHERE id NOT IN (");
    final int _inputSize = excludedIds.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(") ORDER BY RANDOM() LIMIT 1");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (long _item : excludedIds) {
      _statement.bindLong(_argIndex, _item);
      _argIndex++;
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Meal>() {
      @Override
      @Nullable
      public Meal call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfCookingTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "cookingTimeMinutes");
          final int _cursorIndexOfCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "calories");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastGenerated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastGenerated");
          final Meal _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final Difficulty _tmpDifficulty;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfDifficulty);
            _tmpDifficulty = Converters.INSTANCE.toDifficulty(_tmp);
            final int _tmpCookingTimeMinutes;
            _tmpCookingTimeMinutes = _cursor.getInt(_cursorIndexOfCookingTimeMinutes);
            final Integer _tmpCalories;
            if (_cursor.isNull(_cursorIndexOfCalories)) {
              _tmpCalories = null;
            } else {
              _tmpCalories = _cursor.getInt(_cursorIndexOfCalories);
            }
            final List<Category> _tmpCategories;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = Converters.INSTANCE.toCategories(_tmp_1);
            final Date _tmpCreatedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_3 = Converters.INSTANCE.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_3;
            }
            final Date _tmpLastGenerated;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfLastGenerated)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfLastGenerated);
            }
            _tmpLastGenerated = Converters.INSTANCE.fromTimestamp(_tmp_4);
            _result = new Meal(_tmpId,_tmpName,_tmpDescription,_tmpDifficulty,_tmpCookingTimeMinutes,_tmpCalories,_tmpCategories,_tmpCreatedAt,_tmpLastGenerated);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
