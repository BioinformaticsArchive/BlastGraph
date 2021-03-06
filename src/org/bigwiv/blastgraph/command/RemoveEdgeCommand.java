/*
 * BlastGraph: a comparative genomics tool
 * Copyright (C) 2013  Yanbo Ye (yeyanbo289@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

/**
 * 
 */
package org.bigwiv.blastgraph.command;

import java.util.Collection;
import java.util.Map;

import org.bigwiv.blastgraph.BlastGraph;
import org.bigwiv.blastgraph.HitVertex;
import org.bigwiv.blastgraph.ValueEdge;
import org.bigwiv.blastgraph.global.Global;


import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * @author yeyanbo
 *
 */
public class RemoveEdgeCommand extends Command {

	/**
	 * 
	 */
	private BlastGraph<HitVertex, ValueEdge> curSubGraph;
	private Map<ValueEdge, Pair<HitVertex>> removed;
	private VisualizationViewer<HitVertex, ValueEdge> vv;
	
	public RemoveEdgeCommand(BlastGraph<HitVertex, ValueEdge> curSubGraph, Map<ValueEdge, Pair<HitVertex>> removed, VisualizationViewer<HitVertex, ValueEdge> vv) {
		this.isUndoable = true;
		this.commandName = "RemoveEdge";
		this.curSubGraph = curSubGraph;
		this.removed = removed;
		this.vv = vv;
	}

	/* (non-Javadoc)
	 * @see org.bigwiv.blastgraph.command.Command#concreteExecute()
	 */
	@Override
	public void concreteExecute() {
		
		for (ValueEdge ve : removed.keySet()) {
			Global.graph.removeEdge(ve);
			curSubGraph.removeEdge(ve);
		}
		vv.repaint();
		
	}

	/* (non-Javadoc)
	 * @see org.bigwiv.blastgraph.command.Command#concreteUnExecute()
	 */
	@Override
	public void concreteUnExecute() {
		
		for (ValueEdge ve : removed.keySet()) {
			Global.graph.addEdge(ve, removed.get(ve));
			curSubGraph.addEdge(ve, removed.get(ve));
		}
		vv.repaint();
		
	}

}
