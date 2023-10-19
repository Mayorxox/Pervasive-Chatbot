package com.tartu.sensorbot.chat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.test.core.app.ApplicationProvider;
import com.tartu.sensorbot.R;
import com.tartu.sensorbot.message.Message;
import com.tartu.sensorbot.message.MessageStep;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class ChatViewHolderTest {

  private ChatViewHolder viewHolder;
  private Message mockMessage;
  private View itemView;
  private Context context;

  @Before
  public void setUp() {
    context = ApplicationProvider.getApplicationContext();

    // Inflate a sample layout that represents a chat message view.
    mockMessage = Mockito.mock(Message.class);
  }

  @Test
  public void testBindUserMessageWithChatbotConditionReference() {
    itemView = LayoutInflater.from(context).inflate(R.layout.message_item_user, null);
    viewHolder = new ChatViewHolder(itemView, Message.VIEW_TYPE_USER, ChatbotCondition.reference);

    Mockito.when(mockMessage.getText()).thenReturn("Sample text");

    viewHolder.bindUserMessage(mockMessage);

    TextView messageTextView = itemView.findViewById(R.id.messageTextView);

    Assert.assertEquals(Color.WHITE, ((ColorDrawable) messageTextView.getBackground()).getColor());
    Assert.assertEquals("Sample text", messageTextView.getText().toString());
  }

  @Test
  public void testBindComplexBotMessageWithChatbotConditionReference() {
    itemView = LayoutInflater.from(context).inflate(R.layout.message_item_complex_bot, null);
    viewHolder = new ChatViewHolder(itemView, Message.VIEW_TYPE_COMPLEX_BOT,
        ChatbotCondition.reference);

    Mockito.when(mockMessage.getText()).thenReturn("Reference Text");

    viewHolder.bindComplexBotMessage(mockMessage);

    LinearLayout messageContainer = itemView.findViewById(R.id.stepsContainer);
    TextView lastTextView = (TextView) messageContainer.getChildAt(
        messageContainer.getChildCount() - 2);

    Assert.assertEquals("Reference Text", lastTextView.getText().toString());
  }

  @Test
  public void testBindComplexBotMessageWithChatbotConditionPervasive() {
    itemView = LayoutInflater.from(context).inflate(R.layout.message_item_complex_bot, null);
    viewHolder = new ChatViewHolder(itemView, Message.VIEW_TYPE_COMPLEX_BOT,
        ChatbotCondition.pervasive);

    List<MessageStep> steps = Arrays.asList(new MessageStep(1, "Time 1"),
        new MessageStep(2, "Time 2"));
    Mockito.when(mockMessage.getSteps()).thenReturn(steps);

    viewHolder.bindComplexBotMessage(mockMessage);

    LinearLayout messageContainer = itemView.findViewById(R.id.stepsContainer);
    View lastView = messageContainer.getChildAt(messageContainer.getChildCount() - 1);

    // Assuming the complex_bot_pc_button has a button with id confirmButton
    Button confirmButton = lastView.findViewById(R.id.confirmButton);

    Assert.assertNotNull(confirmButton);
  }

}
