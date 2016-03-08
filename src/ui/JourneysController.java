package ui;

import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewFocusModel;
import javafx.scene.control.TableView.TableViewSelectionModel;
import route.JourneyBuilder;
import route.JourneyBuilder.Journey;
import ui.utilities.CtrlKeyListener;
import ui.utilities.UiJourney;
import ui.utilities.UiRoute;

public class JourneysController implements CtrlKeyListener {
	private Controller controller;
	private TableView<UiJourney> journeysTable;
	private TableView<UiRoute> routesTable;
	private JourneyBuilder journeyBuilder;
	private final ObservableList<UiJourney> journeys;
	private final TableViewSelectionModel<UiRoute> journeySelectionModel;
	private final TableViewFocusModel<UiRoute> focusModel;
	//private final ChangeListener<Route> blockChangeListener;
	
	public JourneysController(Controller controller) {
		this.controller = controller;
		journeysTable = this.controller.getJourneysTable();
		routesTable = this.controller.getInterlockTable();
		journeys = journeysTable.getItems();
		
		journeySelectionModel = routesTable.getSelectionModel();
		focusModel = routesTable.getFocusModel();
		
        journeySelectionModel.setSelectionMode(SelectionMode.MULTIPLE);
        journeySelectionModel.selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {        	
        	if (newSelection != null && oldSelection != newSelection && isBuildingJourney()) {
        		journeyBuilder.addToJourney(newSelection.getRoute());
        	}
        });
        
        routesTable.setEditable(false);
        routesTable.getColumns().stream().forEach(col -> col.setSortable(false));
        routesTable.setFocusModel(null);
	}
	
	public void clear() {
		journeysTable.getItems().clear();
	}
	
	public boolean isBuildingJourney() {
		return journeyBuilder != null;
	}

	@Override
	public void onCtrlDown() {
		//start building journey
		if (!isBuildingJourney()) {
			journeyBuilder = new JourneyBuilder();
			routesTable.setFocusModel(focusModel);
			try {
				journeySelectionModel.clearSelection();
			} catch (Exception e) {}
		}
	}

	@Override
	public void onCtrlUp() {
		//end building journey
		if (isBuildingJourney()) {
			try {
				Journey journey = journeyBuilder.build();
				UiJourney uiJourney = new UiJourney(journey);
				
				uiJourney.addChangeListener(change -> {
					if (change.wasRemoved()) {
						//remove journey
						journeys.remove(change.getElement());
					}
				});
				
				journeys.add(uiJourney);
			} catch (IllegalArgumentException e) {}
		}
		
		journeyBuilder = null;
		try {
			journeySelectionModel.clearSelection();
		} catch (Exception e) {}
		routesTable.setFocusModel(null);
	}
}
