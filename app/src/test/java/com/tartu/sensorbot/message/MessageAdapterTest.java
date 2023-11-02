package com.tartu.sensorbot.message;

import com.tartu.sensorbot.chat.ChatbotCondition;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class MessageAdapterTest {

  private MessageAdapter adapter;

  @Before
  public void setUp() {
    adapter = new MessageAdapter(ChatbotCondition.pervasive);
    adapter.setScrollToBottomCallback(() -> {});
  }

  @Test
  public void testGetItemCount_initiallyEmpty() {
    Assertions.assertThat(adapter.getItemCount()).isEqualTo(0);
  }

  @Test
  public void testAddMessage_increasesItemCountByOne() {
    Message mockMessage = Mockito.mock(Message.class);

    adapter.addMessage(mockMessage);
    Assertions.assertThat(adapter.getItemCount()).isEqualTo(1);
  }

  @Test
  public void testClearMessages_resetsItemCountToZero() {
    Message mockMessage = Mockito.mock(Message.class);

    adapter.addMessage(mockMessage);
    adapter.addMessage(mockMessage);
    adapter.clearMessages();
    Assertions.assertThat(adapter.getItemCount()).isEqualTo(0);
  }

  @Test
  public void testAddMessageWithStringAndIsBotFlag_increasesItemCountByOne() {
    adapter.addMessage("Test message", true);
    Assertions.assertThat(adapter.getItemCount()).isEqualTo(1);
  }

  @Test
  public void testAddBotMessages_increasesItemCount() {
    List<Message> mockMessages = Arrays.asList(Mockito.mock(Message.class), Mockito.mock(Message.class), Mockito.mock(Message.class));

    adapter.addBotMessages(mockMessages);
    Assertions.assertThat(adapter.getItemCount()).isEqualTo(3);
  }
}