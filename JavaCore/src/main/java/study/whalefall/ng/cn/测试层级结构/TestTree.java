package study.whalefall.ng.cn.测试层级结构;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 测试树结构的类。
 */
public class TestTree {
    // 存储所有树节点的静态列表
    static List<TreeNode> TreeNodes = new ArrayList<>();

    // 初始化静态列表，创建树的节点
    static {
        TreeNodes.add(new TreeNode(1l, "顶级节点A", 0l));
        TreeNodes.add(new TreeNode(2l, "顶级节点B", 0l));
        TreeNodes.add(new TreeNode(3l, "父节点是A", 1l));
        TreeNodes.add(new TreeNode(4l, "父节点是B", 2l));
        TreeNodes.add(new TreeNode(5l, "父节点是B", 2l));
        TreeNodes.add(new TreeNode(6l, "父节点的ID是3", 3l));
    }

    /**
     * 程序入口。
     * 使用Jackson库将构建的树结构转换为JSON字符串并打印。
     *
     * @param args 命令行参数
     * @throws Exception 如果JSON转换失败
     */
    @SneakyThrows
    public static void main(String[] args) {
        List<TreeNode> tree = getTreeByStreamByReturnValueAndRecursion(TreeNodes);
        ObjectMapper mapper = new ObjectMapper();
        // 将对象数组转换为JSON字符串
        String jsonArray = mapper.writeValueAsString(tree);
        System.out.println(jsonArray);
    }

    /**
     * 通过递归和流式API构建树的分层列表。
     *
     * @param allData 所有树节点的列表
     * @return 构建的树的分层列表
     */
    public static List<TreeNode> getTreeByLevelListAndRecursion(List<TreeNode> allData) {
        // 筛选父节点ID为0的节点作为根节点
        List<TreeNode> rootList = allData.stream().filter(node -> node.getParentId() == 0l).collect(Collectors.toList());
        // 根据父节点ID将节点分组，以便构建子节点关系
        Map<Long, List<TreeNode>> map = allData.stream().collect(Collectors.groupingBy(TreeNode::getParentId));
        // 递归设置每个节点的子节点
        setChildren(rootList, map);
        return rootList;
    }

    /**
     * 递归设置节点的子节点。
     *
     * @param levelList 当前层的节点列表
     * @param map       所有节点的映射，按父节点ID分组
     */
    private static void setChildren(List<TreeNode> levelList, Map<Long, List<TreeNode>> map) {
        levelList.stream().forEach(node -> {
            node.setChildren(map.get(node.getId()));
            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                setChildren(node.getChildren(), map);
            }
        });
    }

    /**
     * 通过流式API和递归构建树结构。
     *
     * @param allData 所有节点的列表
     * @return 构建的树的根节点列表
     */
    public static List<TreeNode> getTreeByStreamByReturnValueAndRecursion(List<TreeNode> allData) {
        return allData.stream().filter(node -> node.getParentId() == 0l).map(node -> {
            node.setChildren(getChildren(allData, node.getId()));
            return node;
        }).collect(Collectors.toList());
    }

    /**
     * 递归获取指定节点的所有子节点。
     *
     * @param allData 所有节点的列表
     * @param id      指定节点的ID
     * @return 指定节点的所有子节点列表
     */
    private static List<TreeNode> getChildren(List<TreeNode> allData, Long id) {
        if (id == null) {
            return null;
        }
        return allData.stream().filter(node -> node.getParentId().equals(id)).map(node -> {
            node.setChildren(getChildren(allData, node.getId()));
            return node;
        }).collect(Collectors.toList());
    }
}
