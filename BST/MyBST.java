import java.util.LinkedList;
import java.util.List;

public class MyBST<E extends Comparable<? super E>> {
	private TreeNode<E> root;
	
	public MyBST() {
		root = null;
	}
	
	public boolean add(E element) {
		boolean newElem = !find(element);
		if(newElem)root = addHelper(element, root);
		return newElem;
	}
	
	private TreeNode<E> addHelper(E element, TreeNode<E> t){
		if(t==null)t = new TreeNode<E>(element);
		else if(element.compareTo(t.element)<0) t.leftChild=addHelper(element,t.leftChild);
		else if(element.compareTo(t.element)>0) t.rightChild=addHelper(element,t.rightChild);
		return t;
	}
	
	public String getAscend() {
		List<TreeNode<E>> nodes = ascendSeq();
		String out = "";
		for(TreeNode<E> i : nodes) {
			out += i.element+";";
		}
		if(out.length()>1)return out.substring(0, out.length()-1);
		return out;
	}
	
	public List<TreeNode<E>> ascendSeq() {
		List<TreeNode<E>> nodes = new LinkedList<>();
		assignNodesInOrder(nodes, root);
		return nodes;
	}
	
	private void assignNodesInOrder(List<TreeNode<E>> list, TreeNode<E> root) {
		if(root!=null) {
			if(root.leftChild!=null)assignNodesInOrder(list, root.leftChild);
			list.add(root);
			if(root.rightChild!=null)assignNodesInOrder(list, root.rightChild);
		}
	}
	public String getDescend() {
		List<TreeNode<E>> nodes = descendSeq();
		String out = "";
		for(TreeNode<E> i : nodes) {
			out += i.element+";";
		}
		if(out.length()>1)return out.substring(0, out.length()-1);
		return out;
	}
	public List<TreeNode<E>> descendSeq() {
		List<TreeNode<E>> nodes = new LinkedList<>();
		assignNodesInOrderRev(nodes, root);
		return nodes;
	}
	private void assignNodesInOrderRev(List<TreeNode<E>> list, TreeNode<E> root) {
		if(root.rightChild!=null)assignNodesInOrderRev(list, root.rightChild);
		list.add(root);
		if(root.leftChild!=null)assignNodesInOrderRev(list, root.leftChild);
	}
	public String getPreOrder() {
		List<TreeNode<E>> nodes = preOrderSeq();
		String out = "";
		for(TreeNode<E> i : nodes) {
			out += i.element+";";
		}
		return out.substring(0, out.length()-1);
	}
	public List<TreeNode<E>> preOrderSeq() {
		List<TreeNode<E>> nodes = new LinkedList<>();
		assignNodesPreOrder(nodes, root);
		return nodes;
	}
	private void assignNodesPreOrder(List<TreeNode<E>> list, TreeNode<E> root) {
		list.add(root);
		if(root.leftChild!=null)assignNodesPreOrder(list, root.leftChild);
		if(root.rightChild!=null)assignNodesPreOrder(list, root.rightChild);
	}
	public String getPostOrder() {
		List<TreeNode<E>> nodes = postOrderSeq();
		String out = "";
		for(TreeNode<E> i : nodes) {
			out += i.element+";";
		}
		return out.substring(0, out.length()-1);
	}
	public List<TreeNode<E>> postOrderSeq() {
		List<TreeNode<E>> nodes = new LinkedList<>();
		assignNodesPostOrder(nodes, root);
		return nodes;
	}
	private void assignNodesPostOrder(List<TreeNode<E>> list, TreeNode<E> root) {
		if(root.leftChild!=null)assignNodesPostOrder(list, root.leftChild);
		if(root.rightChild!=null)assignNodesPostOrder(list, root.rightChild);
		list.add(root);
	}
	public E findMin() {
		return findMinHelper(root).element;
	}
	
	private TreeNode<E> findMinHelper(TreeNode<E> t){
		if(t.leftChild!=null) return findMinHelper(t.leftChild);
		return t;
	}
	
	public E findMax() {
		TreeNode<E> t = root;
		while(t.rightChild!=null)t=t.rightChild;
		return t.element;
	}
	public void remove(E element) {
		removeHelper(element, root);
	}
	private TreeNode<E> removeHelper(E element, TreeNode<E> t){
		if(t==null) return t;
		if(element.compareTo(t.element)<0)t.leftChild=removeHelper(element, t.leftChild);
		else if(element.compareTo(t.element)>0)t.rightChild=removeHelper(element, t.rightChild);
		else {
			if(t.leftChild==null)return t.rightChild;
			else if(t.rightChild==null)return t.leftChild;
			t.element = findMinHelper(t.rightChild).element;
			t.rightChild = removeMin(t.rightChild);
		}
		return t;
	}
	public void removeMin() {
		removeMin(root);
	}
	private TreeNode<E> removeMin(TreeNode<E> t){
		if(t==null) return t;
		if(t.leftChild!=null) {
			t.leftChild = removeMin(t.leftChild);
			return t;
		}
		else return t.rightChild;
	}
	public boolean removeMax() {
		boolean elemExist = findMax() != null;
		if(elemExist)removeMax(root);
		return elemExist;
	}
	private TreeNode<E> removeMax(TreeNode<E> t){
		if(t==null) return t;
		if(t.rightChild!=null) {
			t.rightChild = removeMax(t.rightChild);
			return t;
		}
		else return t.leftChild;
	}
	public boolean find(E element){
		return findHelper(element, root);
	}
	private boolean findHelper(E element, TreeNode<E> t) {
		if(t!=null) {
			if(t.element.equals(element)) return true;
			else if(element.compareTo(t.element)<0) return findHelper(element, t.leftChild);
			return findHelper(element, t.rightChild);
		}
		return false;
		
	}
	public int size() {
		return treeSizer(root);
	}
	private int treeSizer(TreeNode<E> root) {
		if(root==null) return 0;
		return 1 + treeSizer(root.leftChild) + treeSizer(root.rightChild);
	}
	public int height() {
		return heightSizer(root);
	}
	private int heightSizer(TreeNode<E> root) {
		if(root==null) return 0;
		return 1 + Math.max(heightSizer(root.leftChild), heightSizer(root.rightChild));
	}

}

class TreeNode<E extends Comparable<? super E>>{
	protected E element;
	protected TreeNode<E> leftChild;
	protected TreeNode<E> rightChild;
	
	public TreeNode(E element, TreeNode<E> leftChild, TreeNode<E> rightChild) {
		this.element=element;
		this.leftChild=leftChild;
		this.rightChild=rightChild;
	}
	
	public TreeNode(E element) {
		this(element, null, null);
	}
	
	public TreeNode() {
		this(null);
	}
}
