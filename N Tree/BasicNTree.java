import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BasicNTree<E extends Comparable<? super E>> {
	private BasicNode<E> root;
	public BasicNTree() {
		root = new BasicNode<E>(null,null);
	}
	public BasicNTree(E rootName) {
		root = new BasicNode<E>(rootName,null);
	}
	public void add(E data, E parent) {
		add(data,parent,root);
	}
	public void add(E data, E parent, BasicNode<E> node) {
		if(node.data().equals(parent)) {
			node.addChild(data);
			return;
		}
		Map<E,BasicNode<E>> children = node.getChilds();
		if(!node.getChilds().isEmpty()) {
			for(Map.Entry<E,BasicNode<E>> entry : children.entrySet()) {
				add(data,parent,entry.getValue());
			}
		}
	}
	public void remove(E data) {
		remove(data,root);
	}
	public void remove(E data, BasicNode<E> node) {
		if(node.getChilds().containsKey(data)) {
			node.getChilds().remove(data);
		}
		Map<E,BasicNode<E>> children = node.getChilds();
		if(!node.getChilds().isEmpty()) {
			for(Map.Entry<E,BasicNode<E>> entry : children.entrySet()) {
				remove(data,entry.getValue());
			}
		}
	}
	public BasicNode<E> get(E data){
		return get(data, root);
	}
	public BasicNode<E> get(E data, BasicNode<E> node) {
		if(node.data().equals(data)) {
			return node;
		}
		Map<E,BasicNode<E>> children = node.getChilds();
		if(!node.getChilds().isEmpty()) {
			for(Map.Entry<E,BasicNode<E>> entry : children.entrySet()) {
				BasicNode<E> curr = get(data,entry.getValue());
				if(curr!=null) return curr;
			}
		}
		return null;
	}
	private boolean isInside(E target, BasicNode<E> presumed) {
		return presumed.getChilds().containsKey(target);
	}
	public boolean move(E data, E parent, E newLoc) {
		return move(data,get(parent),get(newLoc));
	}
	public boolean move(E data, BasicNode<E> parent, BasicNode<E> newLoc) {
		if(isInside(parent.data(),newLoc)) return false;
		BasicNode<E> t = parent.getChilds().remove(data);
		newLoc.addChild(t);
		return true;
	}
	public String print() {
		return print(root,"");
	}
	public String print(BasicNode<E> t,String indent) {
		String output = indent+"> "+t+"\n";
		String newIndent=indent+"  ";
		Map<E,BasicNode<E>> children = t.getChilds();
		if(!children.isEmpty()) {
			for(Map.Entry<E,BasicNode<E>> entry : children.entrySet()) {
				output+=print(entry.getValue(),newIndent);
			}
		}
		return output;
	}
	public String search(E target) {
		String output = "> "+root+"\n";
		output += search(target,root);
		return output;
	}
	public String printPath(LinkedList<BasicNode<E>> path) {
		String indent = "  ";
		String output = "";
		for(BasicNode<E> i : path) {
			output += indent+"> "+i+"\n";
			indent+="  ";
		}
		return output;
	}
	public String search(E target, BasicNode<E> t) {
		String output = "";
		Map<E,BasicNode<E>> children = t.getChilds();
		if(!children.isEmpty()) {
			for(Map.Entry<E,BasicNode<E>> entry : children.entrySet()) {
				if(entry.getValue().data().equals(target)) output+=printPath(entry.getValue().path());
				output+= search(target, entry.getValue());
			}
		}
		return output;
	}
}

class BasicNode<E extends Comparable<? super E>>{
	private E data;
	private BasicNode<E> parent;
	private Map<E,BasicNode<E>> childs;
	public BasicNode(E data, BasicNode<E> parent) {
		this.data=data;
		this.parent=parent;
		childs= new TreeMap<E,BasicNode<E>>();
	}
	public E data() {
		return data;
	}
	public BasicNode<E> parent(){
		return parent;
	}
	public void changeParent(BasicNode<E> newParent) {
		this.parent=newParent;
	}
	public void set(E data) {
		this.data=data;
	}
	public void addChild(E data) {
		childs.put(data,new BasicNode<E>(data,this));
	}
	public void addChild(BasicNode<E> data) {
		data.changeParent(this);
		childs.put(data.data(),data);
	}
	public Map<E,BasicNode<E>> getChilds(){
		return childs;
	}
	public LinkedList<BasicNode<E>> path(){
		LinkedList<BasicNode<E>> path = new LinkedList<>();
		BasicNode<E> curr = this;
		while(curr.parent()!=null) {
			path.addFirst(curr);
			curr = curr.parent();
		}
		return path;
	}
	public String toString() {
		if(data==null)return "root";
		return ""+data;
	}
}
