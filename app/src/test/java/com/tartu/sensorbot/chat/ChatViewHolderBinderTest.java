package com.tartu.sensorbot.chat;

import com.tartu.sensorbot.R;
import com.tartu.sensorbot.message.Message;
import junit.framework.TestCase;
import org.mockito.Mockito;

public class ChatViewHolderBinderTest extends TestCase {

  public void testGetViewLayoutUser() {
    int viewType = Message.VIEW_TYPE_USER;
    assertEquals(R.layout.message_item_user, ChatViewHolderBinder.getViewLayout(viewType));
  }

  public void testGetViewLayoutBot() {
    int viewType = Message.VIEW_TYPE_BOT;
    assertEquals(R.layout.message_item_bot, ChatViewHolderBinder.getViewLayout(viewType));
  }

  public void testGetViewLayoutDefault() {
    int viewType = 12345;  // Some random view type not handled
    assertEquals(R.layout.message_item_complex_bot, ChatViewHolderBinder.getViewLayout(viewType));
  }

  public void testBindViewUser() {
    ChatViewHolder mockHolder = Mockito.mock(ChatViewHolder.class);
    Message mockMessage = new Message("", Message.VIEW_TYPE_USER);

    ChatViewHolderBinder.bindView(mockHolder, mockMessage);

    Mockito.verify(mockHolder).bindUserMessage(mockMessage);
    Mockito.verify(mockHolder, Mockito.never()).bindBotMessage(mockMessage);
    Mockito.verify(mockHolder, Mockito.never()).bindComplexBotMessage(mockMessage);
  }

  public void testBindViewBot() {
    ChatViewHolder mockHolder = Mockito.mock(ChatViewHolder.class);
    Message mockMessage = new Message("", Message.VIEW_TYPE_BOT);

    ChatViewHolderBinder.bindView(mockHolder, mockMessage);

    Mockito.verify(mockHolder, Mockito.never()).bindUserMessage(mockMessage);
    Mockito.verify(mockHolder).bindBotMessage(mockMessage);
    Mockito.verify(mockHolder, Mockito.never()).bindComplexBotMessage(mockMessage);
  }

  public void testBindViewDefault() {
    ChatViewHolder mockHolder = Mockito.mock(ChatViewHolder.class);
    Message mockMessage = new Message("", Message.VIEW_TYPE_COMPLEX_BOT);

    ChatViewHolderBinder.bindView(mockHolder, mockMessage);

    Mockito.verify(mockHolder, Mockito.never()).bindUserMessage(mockMessage);
    Mockito.verify(mockHolder, Mockito.never()).bindBotMessage(mockMessage);
    Mockito.verify(mockHolder).bindComplexBotMessage(mockMessage);
  }
}
