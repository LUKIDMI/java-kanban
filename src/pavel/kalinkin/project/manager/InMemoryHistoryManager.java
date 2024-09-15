package pavel.kalinkin.project.manager;

import pavel.kalinkin.project.model.Task;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> tasksMap = new HashMap<>();
    private Node firstNode;
    private Node lastNode;

    public static class Node {
        Node next;
        Node prev;
        Task value;

        public Node(Node next, Node prev, Task value) {
            this.next = next;
            this.prev = prev;
            this.value = value;
        }
    }

    @Override
    public void add(Task task) {
        if (tasksMap.containsKey(task.getId())) {
            remove(task.getId());
        }
        linkLast(task);
    }

    public void remove(int id) {
        Node node = tasksMap.get(id);
        removeNode(node);
        tasksMap.remove(id);
    }

    private void linkLast(Task task) {
        int taskId = task.getId();
        Node oldLast = lastNode;
        Node newNode = new Node(null, lastNode, task);
        lastNode = newNode;

        if (oldLast == null) {
            firstNode = newNode;
        } else {
            oldLast.next = newNode;
        }

        tasksMap.put(taskId, newNode);
    }

    private List<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Node n = firstNode;
        for (; n != null; n = n.next) {
            tasks.add(n.value);
        }
        return tasks;
    }

    private void removeNode(Node node) {
        if (node == firstNode) {
            firstNode = node.next;
            if (firstNode != null) {
                firstNode.prev = null;
            } else {
                lastNode = null;
            }
        } else if (node == lastNode) {
            lastNode = node.prev;
            if (lastNode != null) {
                lastNode.next = null;
            } else {
                firstNode = null;
            }
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        tasksMap.remove(node.value.getId());
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }
}




