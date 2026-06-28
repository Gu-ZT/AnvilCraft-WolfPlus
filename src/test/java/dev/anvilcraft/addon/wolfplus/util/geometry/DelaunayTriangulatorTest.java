package dev.anvilcraft.addon.wolfplus.util.geometry;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DelaunayTriangulatorTest {
    @Test
    void triangulatesThreePoints() {
        Set<DelaunayTriangulator.Edge> edges = DelaunayTriangulator.triangulate(List.of(
            new DelaunayTriangulator.Point(0, 0.0, 0.0),
            new DelaunayTriangulator.Point(1, 4.0, 0.0),
            new DelaunayTriangulator.Point(2, 2.0, 3.0)
        ));
        assertEquals(Set.of(
            new DelaunayTriangulator.Edge(0, 1),
            new DelaunayTriangulator.Edge(0, 2),
            new DelaunayTriangulator.Edge(1, 2)
        ), edges);
    }

    @Test
    void triangulatesRectangleWithoutDuplicateEdges() {
        Set<DelaunayTriangulator.Edge> edges = DelaunayTriangulator.triangulate(List.of(
            new DelaunayTriangulator.Point(0, 0.0, 0.0),
            new DelaunayTriangulator.Point(1, 4.0, 0.0),
            new DelaunayTriangulator.Point(2, 4.0, 4.0),
            new DelaunayTriangulator.Point(3, 0.0, 4.0)
        ));
        assertEquals(edges.size(), Set.copyOf(edges).size());
        assertTrue(edges.contains(new DelaunayTriangulator.Edge(0, 1)));
        assertTrue(edges.contains(new DelaunayTriangulator.Edge(1, 2)));
        assertTrue(edges.contains(new DelaunayTriangulator.Edge(2, 3)));
        assertTrue(edges.contains(new DelaunayTriangulator.Edge(0, 3)));
        assertTrue(edges.contains(new DelaunayTriangulator.Edge(0, 2)) || edges.contains(new DelaunayTriangulator.Edge(1, 3)));
    }

    @Test
    void deduplicatesSameProjectedPoint() {
        Set<DelaunayTriangulator.Edge> edges = DelaunayTriangulator.triangulate(List.of(
            new DelaunayTriangulator.Point(0, 0.0, 0.0),
            new DelaunayTriangulator.Point(1, 0.0, 0.0),
            new DelaunayTriangulator.Point(2, 2.0, 0.0),
            new DelaunayTriangulator.Point(3, 1.0, 2.0)
        ));
        assertTrue(edges.stream().noneMatch(edge -> edge.a() == 0 && edge.b() == 1));
        assertTrue(edges.stream().allMatch(edge -> edge.a() != edge.b()));
    }

    @Test
    void handlesNearCollinearPoints() {
        assertDoesNotThrow(() -> DelaunayTriangulator.triangulate(List.of(
            new DelaunayTriangulator.Point(0, 0.0, 0.0),
            new DelaunayTriangulator.Point(1, 1.0, 0.00001),
            new DelaunayTriangulator.Point(2, 2.0, -0.00001),
            new DelaunayTriangulator.Point(3, 3.0, 0.00002)
        )));
    }
}
