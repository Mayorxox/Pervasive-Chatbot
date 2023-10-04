package com.tartu.sensorbot.message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tartu.sensorbot.chat.ChatViewHolder;
import com.tartu.sensorbot.chat.ChatViewHolderBinder;
import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<ChatViewHolder> {

  private final List<Message> messages = new ArrayList<>();
  private final String condition;
  private Runnable scrollToBottomCallback;

  public MessageAdapter(String condition) {
    this.condition = condition;
  }

  @Override
  public int getItemViewType(int position) {
    return messages.get(position).getViewType();
  }

  @NonNull
  @Override
  public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    int viewLayout = ChatViewHolderBinder.getViewLayout(viewType);
    View view = LayoutInflater.from(parent.getContext()).inflate(viewLayout, parent, false);
    return new ChatViewHolder(view, viewType, condition);
  }

  @Override
  public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
    Message message = messages.get(position);
    ChatViewHolderBinder.bindView(holder, message);
  }

  public void addMessage(Message message) {
    messages.add(message);
    notifyItemInserted(messages.size() - 1);
    scrollToBottomCallback.run();
  }

  public void clearMessages() {
    messages.clear();
  }

  public void addMessage(String text, boolean isBot) {
    Message message = new Message(text, isBot);
    addMessage(message);
  }

  public void addBotMessages(List<Message> newMessages) {
    newMessages.forEach(this::addMessage);
  }

  @Override
  public int getItemCount() {
    return messages.size();
  }

  public void setScrollToBottomCallback(Runnable callback) {
    this.scrollToBottomCallback = callback;
  }
}
