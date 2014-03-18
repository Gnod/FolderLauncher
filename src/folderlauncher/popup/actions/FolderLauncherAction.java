package folderlauncher.popup.actions;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.internal.core.JavaElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.PluginAction;
import org.eclipse.ui.part.FileEditorInput;

import folderlauncher.popup.util.SystemHelper;

public class FolderLauncherAction implements IObjectActionDelegate {

	private Shell shell;
	
	private String browser;
	
	/**
	 * Constructor for Action1.
	 */
	public FolderLauncherAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		if(action instanceof PluginAction) {
			browser = SystemHelper.getInstance().getBrowser();
			IResource resource = null;
			ISelection selection = (ISelection) ((PluginAction)action).getSelection();

			Object element = ((StructuredSelection) selection).getFirstElement();
			if (element instanceof IResource) {
				resource = (IResource) element;
			} else if (element instanceof JavaElement) {
				resource = (IResource) ((JavaElement) element).getResource();
			} else if(element instanceof FileEditorInput) {
				resource = ((FileEditorInput) element).getFile().getParent();
			}
			
			if(resource == null)
				return;
			
			if(resource instanceof IFile) {
				resource = resource.getParent();
			}
			
			String filePath = resource.getLocation().toOSString();
			
			try {
				if(SystemHelper.getInstance().isWindows()) {
					Runtime.getRuntime().exec(browser + " \"" + filePath + "\"");
				} else {
					Runtime.getRuntime().exec(new String[] {browser, filePath});
				}
			} catch (IOException e) {
				e.printStackTrace();
				MessageDialog.openError(shell, "Open Failed", "Can't Open \"" + filePath + "\"");
			}
		}
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
